package com.example.myapplication.ui.note;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.Category;
import com.example.myapplication.database.Note;
import com.example.myapplication.database.Priority;
import com.example.myapplication.database.Status;

import java.lang.reflect.Array;
import java.util.List;

public class EditNoteDialog extends DialogFragment {

    static EditNoteDialog end;
    AppDatabase db;
    NoteAdapter noteAdapter;
    Spinner spinner_priority;
    Spinner spinner_category;
    Spinner spinner_status;
    public static TextView plan_date;
    int userId;


    public static EditNoteDialog getInstance(int id) {
        if(end == null) {
            end = new EditNoteDialog();
        }
        Bundle args = new Bundle();
        args.putInt("id", id);
        end.setArguments(args);
        return end;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.note_dialog, container, false);
        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        userId = getArguments().getInt("id");
        Dialog d = new AlertDialog.Builder(getActivity())
                .create();
        d.setContentView(R.layout.note_dialog);

        return d;
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

}
