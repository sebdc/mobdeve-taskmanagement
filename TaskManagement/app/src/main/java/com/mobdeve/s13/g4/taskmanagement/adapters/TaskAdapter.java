package com.mobdeve.s13.g4.taskmanagement.adapters;

import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.activities.ViewTaskActivity;
import com.mobdeve.s13.g4.taskmanagement.models.*;
import com.mobdeve.s13.g4.taskmanagement.viewholders.*;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private List<Task> taskList;
    private String currentDate = "";

    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        // - Check if the new task's due date is different from the current date
        String taskDueDate = task.getDueDate();

        if( !taskDueDate.equals(currentDate) ) {
            holder.getTaskDueDate().setVisibility(View.VISIBLE);
            holder.getTaskDueDate().setText(taskDueDate);
            currentDate = taskDueDate;
        }

        if( task.getTitle() != null ) {
            holder.getTaskTitle().setText(task.getTitle());
        }

        if( task.getDueDate() != null ) {
            holder.getTaskDueDate().setText(task.getDueDate());
        }

        if( task.getCategory() != null && task.getCategory().getName() != null ) {
            holder.getTaskCategory().setText(task.getCategory().getName());
        }

        if( task.getPriorityLevel() != null ) {
            holder.getTaskPriority().setText(task.getPriorityLevel());
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ViewTaskActivity.class);
            intent.putExtra("task", task);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
        return dateFormat.format(date);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}