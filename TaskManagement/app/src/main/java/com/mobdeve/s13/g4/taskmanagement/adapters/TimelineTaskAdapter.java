package com.mobdeve.s13.g4.taskmanagement.adapters;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.activities.ViewTaskActivity;
import com.mobdeve.s13.g4.taskmanagement.adapters.*;
import com.mobdeve.s13.g4.taskmanagement.models.*;
import com.mobdeve.s13.g4.taskmanagement.database.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.app.Activity;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimelineTaskAdapter extends RecyclerView.Adapter<TimelineTaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private ActivityResultLauncher<Intent> updateTaskLauncher;

    public TimelineTaskAdapter( List<Task> taskList, ActivityResultLauncher<Intent> updateTaskLauncher ) {
        this.taskList = taskList;
        this.updateTaskLauncher = updateTaskLauncher;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeline_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder( @NonNull TaskViewHolder holder, int position ) {
        Task task = taskList.get(position);
        holder.bind(task);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ViewTaskActivity.class);
            intent.putExtra("task", task);
            updateTaskLauncher.launch(intent);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFlagIcon;
        private TextView titleTextView;
        private TextView tvCategoryTag;

        public TaskViewHolder( @NonNull View itemView ) {
            super(itemView);
            ivFlagIcon = itemView.findViewById(R.id.ivFlagIcon);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            tvCategoryTag = itemView.findViewById(R.id.tvCategoryTag);
        }

        public void bind( Task task ) {
            titleTextView.setText(task.getTitle());
        }
    }
}