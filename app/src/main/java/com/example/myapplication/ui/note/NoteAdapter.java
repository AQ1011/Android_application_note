package com.example.myapplication.ui.note;

import android.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.Category;
import com.example.myapplication.database.Note;
import com.example.myapplication.database.Priority;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> notes;
    onLongItemClickListener mOnLongItemClickListener;
    AppDatabase db;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        final AppDatabase db;
        final TextView noteName;
        final TextView dateCreated;
        final TextView datePlan;
        final TextView priority;
        final TextView status;
        final TextView category;
        int noteId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnCreateContextMenuListener(this);

            db = AppDatabase.getDatabase(itemView.getContext());

            noteName = itemView.findViewById(R.id.tv_note_name);
            dateCreated = itemView.findViewById(R.id.tv_note_created_date);
            priority = itemView.findViewById(R.id.tv_note_priority);
            datePlan = itemView.findViewById(R.id.tv_note_plan_date);
            status = itemView.findViewById(R.id.tv_note_status);
            category = itemView.findViewById(R.id.tv_note_category);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Edit = menu.add(0, 0, 0, "Edit");
            MenuItem Delete = menu.add(0, 1, 0, "Delete");
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        db = AppDatabase.getDatabase(holder.dateCreated.getContext());



        holder.dateCreated.setText("Created date: " + notes.get(position).created_date);
        holder.datePlan.setText("Plan date: " + notes.get(position).plan_date);
        holder.noteName.setText("Name: " + notes.get(position).name);
        holder.category.setText("Category: " + notes.get(position).category);
        holder.priority.setText("Priority: " + notes.get(position).priority);
        holder.status.setText("Status: " + notes.get(position).status);
        holder.noteId = notes.get(position).id;

        holder.itemView.setOnLongClickListener(v -> {
            if (mOnLongItemClickListener != null) {
                mOnLongItemClickListener.ItemLongClicked(v, position);
                v.showContextMenu();
            }
            return true;
        });
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

    public int getNoteID(int position){
        return notes.get(position).id;
    }


    public void setOnLongItemClickListener(onLongItemClickListener onLongItemClickListener) {
        mOnLongItemClickListener = onLongItemClickListener;
    }

    public interface onLongItemClickListener {
        void ItemLongClicked(View v, int position);
    }
}
