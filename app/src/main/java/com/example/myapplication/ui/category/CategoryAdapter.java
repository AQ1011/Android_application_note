package com.example.myapplication.ui.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> categories;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView categoryName;
        final TextView dateCreated;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryName = itemView.findViewById(R.id.tv_category_name);
            dateCreated = itemView.findViewById(R.id.tv_category_date_created);

        }
    }

    public CategoryAdapter(List<Category> categories){
        this.categories = categories;
        if(this.categories == null){
            this.categories = new ArrayList<Category>();
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
        holder.dateCreated.setText(categories.get(position).created_date);
        holder.categoryName.setText(categories.get(position).name);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void addCategory(Category category){
        categories.add(category);
        notifyItemInserted(getItemCount());
    }

    public void removeAt(int position){
        categories.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,categories.size());
    }
}
