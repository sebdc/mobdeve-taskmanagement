package com.mobdeve.s13.g4.taskmanagement.viewholders;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.adapters.*;
import com.mobdeve.s13.g4.taskmanagement.models.*;
import com.mobdeve.s13.g4.taskmanagement.database.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

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