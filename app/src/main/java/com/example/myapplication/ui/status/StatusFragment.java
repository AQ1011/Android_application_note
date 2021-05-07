package com.example.myapplication.ui.status;

import android.app.AlertDialog;
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
import com.example.myapplication.database.Status;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.util.List;

public class StatusFragment extends Fragment {
    List<Status> statuses;
    StatusAdapter statusAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_status, container, false);

        AppDatabase db = AppDatabase.getDatabase(getContext());

        RecyclerView categoryView = root.findViewById(R.id.recycle_view_status);

        db.getQueryExecutor().execute(() -> {
            statuses = db.statusDao().getAll();
            this.getActivity().runOnUiThread(() -> {
                statusAdapter = new StatusAdapter(statuses);
                categoryView.setAdapter(statusAdapter);
                categoryView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            });
        });


        AlertDialog al = new AlertDialog.Builder(root.getContext()).create();
        View view = getLayoutInflater().inflate(R.layout.dialog_category, null);
        Button btnAdd = view.findViewById(R.id.btn_category_add);
        EditText edCategory = view.findViewById(R.id.edittext_category);
        TextView tv = view.findViewById(R.id.textView_dialog_title);
        tv.setText("Status Form");
        al.setView(view);

        btnAdd.setOnClickListener(v1 -> {
            Status c = new Status();
            c.name = edCategory.getText().toString();
            c.created_date = LocalDateTime.now().toString();
            AppDatabase.databaseWriteExecutor.execute(()->{
                if(db.statusDao().findName(c.name) != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Đã tồn tại status này", Toast.LENGTH_SHORT).show();
                    });
                    return;
                }
                db.statusDao().insert(c);
                this.getActivity().runOnUiThread(() -> {

                    statusAdapter.add(c);
                    statusAdapter.notifyItemInserted(statusAdapter.getItemCount());
                    edCategory.setText("");

                });
            });
            al.cancel();
        });

        FloatingActionButton fl = root.findViewById(R.id.floating_status);
        fl.setOnClickListener(v -> {
            al.show();
        });
        return root;
    }
}
