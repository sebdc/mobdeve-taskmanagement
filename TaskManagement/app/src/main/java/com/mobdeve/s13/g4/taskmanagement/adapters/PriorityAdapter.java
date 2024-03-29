package com.mobdeve.s13.g4.taskmanagement.adapters;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.adapters.*;
import com.mobdeve.s13.g4.taskmanagement.models.*;
import com.mobdeve.s13.g4.taskmanagement.database.*;
import com.mobdeve.s13.g4.taskmanagement.viewholders.*;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class PriorityAdapter extends RecyclerView.Adapter<PriorityAdapter.PriorityViewHolder> {

    private List<String> priorityList;
    private int selectedPosition = -1;
    private OnPriorityClickListener listener;

    public interface OnPriorityClickListener {
        void onPriorityClick(String priority);
    }

    public PriorityAdapter(List<String> priorityList, OnPriorityClickListener listener) {
        this.priorityList = priorityList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PriorityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_priority, parent, false);
        return new PriorityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PriorityViewHolder holder, int position) {
        String priority = priorityList.get(position);
        holder.bind(priority);
    }

    @Override
    public int getItemCount() {
        return priorityList.size();
    }

    public class PriorityViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPriorityName;
        private LinearLayout priorityItemLayout;

        public PriorityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPriorityName = itemView.findViewById(R.id.tvPriorityName);
            priorityItemLayout = itemView.findViewById(R.id.priorityItemLayout);
        }

        public void bind(String priority) {
            tvPriorityName.setText(priority);

            if (getAdapterPosition() == selectedPosition) {
                priorityItemLayout.setBackgroundResource(R.drawable.selected_priority_background);
            } else {
                priorityItemLayout.setBackgroundResource(0);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onPriorityClick(priority);
                    }
                    selectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}