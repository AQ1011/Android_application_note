package com.example.myapplication.ui.note;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.Category;
import com.example.myapplication.database.Note;
import com.example.myapplication.database.Priority;
import com.example.myapplication.database.Status;
import com.example.myapplication.ui.priority.PriorityAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteFragment extends Fragment {
    private List<Note> notes;
    private NoteAdapter noteAdapter;
    private Spinner spinner_priority;
    private Spinner spinner_category;
    private Spinner spinner_status;
    public static TextView plan_date;
    private int userId;
    private int mCurrentItemPosition;
    public static AlertDialog al;
    private int categoryId;
    private int statusId;
    private int priorityId;
    private EditText edName;

    AppDatabase db;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_note, container, false);


        db = AppDatabase.getDatabase(getContext());

        RecyclerView noteView = root.findViewById(R.id.recycler_view_note);

        SharedPreferences sharedPref = getContext().getSharedPreferences("NOTE_SHARED_PREFERENCE"
                , Context.MODE_PRIVATE);

        String username = sharedPref.getString("username","Default name");
        userId = sharedPref.getInt("userId", 0);

        db.getQueryExecutor().execute(() -> {
            notes = db.noteDao().getUserNotes(userId);
            this.getActivity().runOnUiThread(() -> {
                noteAdapter = new NoteAdapter(notes);
                noteView.setAdapter(noteAdapter);
                noteAdapter.setOnLongItemClickListener((v,position) -> {
                        mCurrentItemPosition = position;
                    });
                noteView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            });
        });


        FloatingActionButton fl = (FloatingActionButton)root.findViewById(R.id.floating_note);
        al = new AlertDialog.Builder(root.getContext()).create();
        View view = getLayoutInflater().inflate(R.layout.note_dialog, null);
        Button btnAdd = view.findViewById(R.id.btn_note_add);

        al.setView(view);


        Button btnPickDate = view.findViewById(R.id.btn_pick_date);
        edName = (EditText)view.findViewById(R.id.ed_note_name);
        spinner_priority = (Spinner)view.findViewById(R.id.spinner_priority);
        spinner_category = (Spinner)view.findViewById(R.id.spinner_category);
        spinner_status = (Spinner)view.findViewById(R.id.spinner_status);
        plan_date = view.findViewById(R.id.tv_note_plan_date);

        btnAdd.setOnClickListener(v -> {
            insert();
        });

        btnPickDate.setOnClickListener(v -> {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getChildFragmentManager(), "datePicker");
        });

        fl.setOnClickListener(v ->{
            btnAdd.setText("Add");
            btnAdd.setOnClickListener(v1 -> {
                insert();
            });
            al.show();
        });

        fillspinner();

        return root;
    }

    void insert(){
        Note n = new Note();
        n.name = edName.getText().toString();
        if(spinner_status.getSelectedItem()== null ||
                spinner_priority.getSelectedItem()== null ||
                spinner_category.getSelectedItem()== null )
        {
            Toast.makeText(getContext(), "Chưa chọn đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        n.category = ((Category) spinner_category.getSelectedItem()).id;
        n.priority = ((Priority) spinner_priority.getSelectedItem()).id;
        n.status = ((Status) spinner_status.getSelectedItem()).id;
        n.plan_date = plan_date.getText().toString();
        n.user = userId;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        n.created_date = sdf.format(Calendar.getInstance().getTime());

        if(n.plan_date.equals("Select plan date") )
        {
            Toast.makeText(getContext(), "Chưa chọn ngày", Toast.LENGTH_SHORT).show();
            return;
        }
        db.getQueryExecutor().execute(() -> {
            db.noteDao().insert(n);
            notes = db.noteDao().getUserNotes(userId);
        });
        al.cancel();
        noteAdapter.notifyDataSetChanged();
    }

    void fillspinner(){
        db.getQueryExecutor().execute(() -> {
            List<Category> categories = db.categoryDao().getUserCategory(userId);
            ArrayAdapter categoryAdapter = new ArrayAdapter(getContext(), R.layout.spinner_category, categories);
            getActivity().runOnUiThread(() -> {
                spinner_category.setAdapter(categoryAdapter);
            });

            List<Priority> priorities = db.priorityDao().getUserPriority(userId);
            ArrayAdapter priorityAdapter = new ArrayAdapter(getContext(), R.layout.spinner_category, priorities);
            getActivity().runOnUiThread(() -> {
                spinner_priority.setAdapter(priorityAdapter);
            });

            List<Status> statuses = db.statusDao().getUserStatus(userId);
            ArrayAdapter statusAdapter = new ArrayAdapter(getContext(), R.layout.spinner_category, statuses);
            getActivity().runOnUiThread(() -> {
                spinner_status.setAdapter(statusAdapter);
            });
        });
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            plan_date.setText(String.valueOf(day)+"/"+String.valueOf(month)+"/"+
                    String.valueOf(year));
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int noteId = noteAdapter.getNoteID(mCurrentItemPosition);
        switch (item.getItemId()) {
            case 0:
                al.show();
                Button btn = al.findViewById(R.id.btn_note_add);
                btn.setText("Update");
                btn.setOnClickListener(v -> {
                    db.getQueryExecutor().execute(() -> {
                        Note n = db.noteDao().find(noteId);
                        n.name = edName.getText().toString();
                        if(spinner_status.getSelectedItem()== null ||
                                spinner_priority.getSelectedItem()== null ||
                                spinner_category.getSelectedItem()== null )
                        {
                            getActivity().runOnUiThread(() -> {
                                Toast.makeText(getContext(), "Chưa chọn đủ thông tin", Toast.LENGTH_SHORT).show();
                                return;
                            });
                        }

                        n.category = ((Category) spinner_category.getSelectedItem()).id;
                        n.priority = ((Priority) spinner_priority.getSelectedItem()).id;
                        n.status = ((Status) spinner_status.getSelectedItem()).id;
                        n.plan_date = plan_date.getText().toString();
                        n.user = userId;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                        n.created_date = sdf.format(Calendar.getInstance().getTime());

                        if(n.plan_date.equals("Select plan date") )
                        {
                            getActivity().runOnUiThread(() -> {
                                Toast.makeText(getContext(), "Chưa chọn ngày", Toast.LENGTH_SHORT).show();
                            });
                            return;
                        }
                        db.noteDao().update(n);
                        notes.set(mCurrentItemPosition,n);
                    });
                    al.cancel();
                    noteAdapter.notifyDataSetChanged();
                });
                break;
            case 1:
                noteAdapter.removeAt(mCurrentItemPosition);
                AppDatabase db = AppDatabase.getDatabase(getContext());
                db.getQueryExecutor().execute(() -> {
                    db.noteDao().delete(
                            db.noteDao().find(noteId));
                });
                break;
        }
        return super.onContextItemSelected(item);
    }
}
