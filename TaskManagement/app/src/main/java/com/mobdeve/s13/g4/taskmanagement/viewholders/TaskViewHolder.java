package com.mobdeve.s13.g4.taskmanagement.viewholders;

import com.mobdeve.s13.g4.taskmanagement.adapters.*;
import com.mobdeve.s13.g4.taskmanagement.models.*;
import com.mobdeve.s13.g4.taskmanagement.viewholders.*;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s13.g4.taskmanagement.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    TextView taskDueDate;
    TextView taskTitle;
    TextView taskDueDateTime;
    TextView taskCategory;
    TextView taskPriority;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        taskDueDate = itemView.findViewById(R.id.taskDueDate);
        taskTitle = itemView.findViewById(R.id.taskTitle);
        taskDueDateTime = itemView.findViewById(R.id.taskDueDateTime);
        taskCategory = itemView.findViewById(R.id.taskCategory);
        taskPriority = itemView.findViewById(R.id.taskPriority);
    }

    public void bind(Task task) {
        taskTitle.setText(task.getTitle());
        taskPriority.setText("None");
    }

    public TextView getTaskDueDate() {
        return taskDueDate;
    }

    public TextView getTaskTitle() {
        return taskTitle;
    }

    public TextView getTaskCategory() {
        return taskCategory;
    }

    public TextView getTaskPriority() {
        return taskPriority;
    }
}