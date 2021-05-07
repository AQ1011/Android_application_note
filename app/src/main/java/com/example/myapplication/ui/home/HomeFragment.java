package com.example.myapplication.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.Note;
import com.example.myapplication.database.Status;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    List<Note> notes;
    List<Status> statuses;
    int userId;
    ArrayList noteStatus;
    ArrayList statusName;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        PieChart pieChart = (PieChart)root.findViewById(R.id.chart);
        AppDatabase db = AppDatabase.getDatabase(getContext());

        SharedPreferences sharedPref = getContext().getSharedPreferences("NOTE_SHARED_PREFERENCE"
                , Context.MODE_PRIVATE);

        String user = sharedPref.getString("username","Default name");
        userId = sharedPref.getInt("userId", 0);

        noteStatus = new ArrayList();
        statusName = new ArrayList();

        db.getQueryExecutor().execute(() -> {
            notes = db.noteDao().getUserNotes(userId);
            statuses = db.statusDao().getUserStatus(userId);
            if(statuses.size() == 0)
                return;
            int data = 0;
            for (Status status: statuses
                 ) {
                String name = status.name;
                int numberOfNote = 0;
                for (Note note : notes
                     ) {
                    if(note.status == status.id){
                        numberOfNote += 1;
                    }
                }
                noteStatus.add(new PieEntry(numberOfNote, name));
                statusName.add(name);
                data += 1;
            }
        });

        PieDataSet dataSet = new PieDataSet(noteStatus, "");

        PieData data = new PieData();
        data.setDataSet(dataSet);
        pieChart.setData(data);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.setDrawHoleEnabled(false);

        return root;
    }
}