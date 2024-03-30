package com.mobdeve.s13.g4.taskmanagement.viewholders;

import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.adapters.*;
import com.mobdeve.s13.g4.taskmanagement.models.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.widget.TextView;

public class AssignCategoryViewHolder extends RecyclerView.ViewHolder {

    private TextView tvCategoryName;

    public AssignCategoryViewHolder( @NonNull View itemView ) {
        super(itemView);
        tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
    }

    public void bind( Category category, AssignCategoryAdapter.OnCategoryClickListener listener ) {
        tvCategoryName.setText(category.getName());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( listener != null ) {
                    listener.onCategoryClick(category);
                }
            }
        });
    }
}