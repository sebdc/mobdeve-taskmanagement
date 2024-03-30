package com.mobdeve.s13.g4.taskmanagement.adapters;

import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.viewholders.CreateCategoryViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class CreateCategoryAdapter extends RecyclerView.Adapter<CreateCategoryViewHolder> {

    private List<Integer> colors;
    private OnColorClickListener listener;

    public interface OnColorClickListener {
        void onColorClick(int color);
    }

    public CreateCategoryAdapter(List<Integer> colors, OnColorClickListener listener) {
        this.colors = colors;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CreateCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_create_category_color, parent, false);
        return new CreateCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateCategoryViewHolder holder, int position) {
        int color = colors.get(position);
        holder.bind(color, listener);
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }
}