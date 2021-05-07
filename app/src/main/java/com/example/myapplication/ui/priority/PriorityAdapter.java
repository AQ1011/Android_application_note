package com.example.myapplication.ui.priority;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.Category;
import com.example.myapplication.database.Priority;

import java.util.ArrayList;
import java.util.List;

public class PriorityAdapter extends RecyclerView.Adapter<PriorityAdapter.ViewHolder> {

    private List<Priority> priorities;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView categoryName;
        final TextView dateCreated;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryName = itemView.findViewById(R.id.tv_category_name);
            dateCreated = itemView.findViewById(R.id.tv_category_date_created);

        }
    }

    public PriorityAdapter(List<Priority> priorities){
        this.priorities = priorities;
        if(this.priorities == null){
            this.priorities = new ArrayList<Priority>();
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
        holder.dateCreated.setText(priorities.get(position).created_date);
        holder.categoryName.setText(priorities.get(position).name);
    }

    @Override
    public int getItemCount() {
        return priorities.size();
    }

    public void addCategory(Priority priority){
        priorities.add(priority);
        notifyItemInserted(getItemCount());
    }

    public void removeAt(int position){
        priorities.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,priorities.size());
    }
}
