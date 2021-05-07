package com.example.myapplication.ui.status;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.Category;
import com.example.myapplication.database.Priority;
import com.example.myapplication.database.Status;

import java.util.ArrayList;
import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {

    private List<Status> statuses;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView categoryName;
        final TextView dateCreated;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryName = itemView.findViewById(R.id.tv_category_name);
            dateCreated = itemView.findViewById(R.id.tv_category_date_created);

        }
    }

    public StatusAdapter(List<Status> statuses){
        this.statuses = statuses;
        if(this.statuses == null){
            this.statuses = new ArrayList<Status>();
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
        holder.dateCreated.setText(statuses.get(position).created_date);
        holder.categoryName.setText(statuses.get(position).name);
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }

    public void add(Status status){
        statuses.add(status);
        notifyItemInserted(getItemCount());
    }

    public void removeAt(int position){
        statuses.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,statuses.size());
    }
}
