package com.example.myapplication.ui.priority;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.Priority;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.util.List;

public class PriorityFragment extends Fragment {
    List<Priority> priorities;
    PriorityAdapter priorityAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_priority, container, false);

        AppDatabase db = AppDatabase.getDatabase(getContext());

        RecyclerView categoryView = root.findViewById(R.id.recycle_view_prioriy);

        SharedPreferences sharedPref = getContext().getSharedPreferences("NOTE_SHARED_PREFERENCE"
                , Context.MODE_PRIVATE);

        String user = sharedPref.getString("username","Default name");
        int userId = sharedPref.getInt("userId", 0);

        db.getQueryExecutor().execute(() -> {
            priorities = db.priorityDao().getUserPriority(userId);
            this.getActivity().runOnUiThread(() -> {
                priorityAdapter = new PriorityAdapter(priorities);
                categoryView.setAdapter(priorityAdapter);
                categoryView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            });
        });


        AlertDialog al = new AlertDialog.Builder(root.getContext()).create();
        View view = getLayoutInflater().inflate(R.layout.dialog_category, null);
        Button btnAdd = view.findViewById(R.id.btn_category_add);
        EditText edCategory = view.findViewById(R.id.edittext_category);
        TextView tv = view.findViewById(R.id.textView_dialog_title);

        tv.setText("Priority Form");
        al.setView(view);

        btnAdd.setOnClickListener(v1 -> {
            Priority c = new Priority();
            c.name = edCategory.getText().toString();
            c.created_date = LocalDateTime.now().toString();
            c.user = userId;
            AppDatabase.databaseWriteExecutor.execute(()->{
                if(db.priorityDao().findNameUser(c.name, userId) != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Đã tồn tại priority này", Toast.LENGTH_SHORT).show();
                    });
                    return;
                }
                db.priorityDao().insert(c);
                this.getActivity().runOnUiThread(() -> {

                    priorityAdapter.addCategory(c);
                    priorityAdapter.notifyItemInserted(priorityAdapter.getItemCount());
                    edCategory.setText("");

                });
            });
            al.cancel();
        });

        FloatingActionButton fl = root.findViewById(R.id.floating_priority);
        fl.setOnClickListener(v -> {
            al.show();
        });
        return root;
    }
}
