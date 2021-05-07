package com.example.myapplication.ui.note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.Category;
import com.example.myapplication.database.Note;
import com.example.myapplication.database.Priority;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> notes;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView noteName;
        final TextView dateCreated;
        final TextView datePlan;
        final TextView priority;
        final TextView status;
        final TextView category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            noteName = itemView.findViewById(R.id.tv_note_name);
            dateCreated = itemView.findViewById(R.id.tv_note_created_date);
            priority = itemView.findViewById(R.id.tv_note_priority);
            datePlan = itemView.findViewById(R.id.tv_note_plan_date);
            status = itemView.findViewById(R.id.tv_note_status);
            category = itemView.findViewById(R.id.tv_note_category);
        }
    }

    public NoteAdapter(List<Note> notes){
        this.notes = notes;
        if(this.notes == null){
            this.notes = new ArrayList<Note>();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dateCreated.setText(notes.get(position).created_date);
        holder.noteName.setText(notes.get(position).name);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void addCategory(Note note){
        notes.add(note);
        notifyItemInserted(getItemCount());
    }

    public void removeAt(int position){
        notes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,notes.size());
    }
}
