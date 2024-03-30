package com.mobdeve.s13.g4.taskmanagement.adapters;

import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.models.*;

import com.mobdeve.s13.g4.taskmanagement.viewholders.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class AssignCategoryAdapter extends RecyclerView.Adapter<AssignCategoryViewHolder> {

    // - Class Attributes
    private List<Category> categories;
    private OnCategoryClickListener listener;

    /*|*******************************************************
                      Constructor Methods
    *********************************************************/
    public AssignCategoryAdapter( List<Category> categories, OnCategoryClickListener listener ) {
        this.categories = categories;
        this.listener = listener;
    }

    public interface OnCategoryClickListener {
        void onCategoryClick( Category category );
    }

    @NonNull
    @Override
    public AssignCategoryViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assign_category, parent, false);
        return new AssignCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignCategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category, listener);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}