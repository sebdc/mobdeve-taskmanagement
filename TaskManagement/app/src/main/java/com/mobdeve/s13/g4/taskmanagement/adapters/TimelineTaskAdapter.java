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

import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.PorterDuff;
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
    private OnTaskClickListener onTaskClickListener;

    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }

    public TimelineTaskAdapter( List<Task> taskList, OnTaskClickListener onTaskClickListener ) {
        this.taskList = taskList;
        this.onTaskClickListener = onTaskClickListener;
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
            if( onTaskClickListener != null ) {
                onTaskClickListener.onTaskClick(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    /*|*******************************************************
                        Task View Holder
    *********************************************************/
    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFlagIcon;
        private TextView titleTextView;
        private TextView tvCategoryTag;

        public TaskViewHolder( @NonNull View itemView ) {
            super(itemView);
            findAllViews();
        }

        private void findAllViews() {
            ivFlagIcon = itemView.findViewById(R.id.ivFlagIcon);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            tvCategoryTag = itemView.findViewById(R.id.tvCategoryTag);
        }


        public void bind( Task task ) {
            titleTextView.setText(task.getTitle());

            // - Show Task Category
            if( task.getCategory() != null ) {
                Category category = task.getCategory();

                tvCategoryTag.setText(task.getCategory().getName());
                tvCategoryTag.setVisibility(View.VISIBLE);

                String categoryColor = category.getMainColor();

                // - Update Task Category color if it exists
                if( categoryColor != null && !categoryColor.isEmpty() ) {
                    try {
                        int color = Color.parseColor(categoryColor);
                        tvCategoryTag.setTextColor(color);

                        // - 10% opacity
                        int backgroundColorWithOpacity = ColorUtils.setAlphaComponent(color, (int) (255 * 0.1));
                        tvCategoryTag.getBackground().setColorFilter(backgroundColorWithOpacity, PorterDuff.Mode.SRC_IN);
                    } catch (IllegalArgumentException e) {
                        tvCategoryTag.setTextColor(Color.BLACK);
                        tvCategoryTag.getBackground().setColorFilter(null);
                    }
                }
            } else {
                tvCategoryTag.setVisibility(View.GONE);
            }

            // - Change Priority Flag Color
            if( task.getPriorityLevel() != null ) {
                String priorityLevel = task.getPriorityLevel();
                handleFlagIconColor(priorityLevel);
            } else {
                ivFlagIcon.setVisibility(View.GONE);
            }
        }

        private void handleFlagIconColor( String priorityLevel ) {
            switch( priorityLevel ) {
                case "High": {
                    ivFlagIcon.setVisibility(View.VISIBLE);
                    ivFlagIcon.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.high), PorterDuff.Mode.SRC_IN);
                }
                break;
                case "Medium": {
                    ivFlagIcon.setVisibility(View.VISIBLE);
                    ivFlagIcon.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.medium), PorterDuff.Mode.SRC_IN);
                }
                break;
                case "Low": {
                    ivFlagIcon.setVisibility(View.VISIBLE);
                    ivFlagIcon.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.low), PorterDuff.Mode.SRC_IN);
                }
                break;
                default: {
                    ivFlagIcon.setVisibility(View.GONE);
                }
            }
        }
    }
}