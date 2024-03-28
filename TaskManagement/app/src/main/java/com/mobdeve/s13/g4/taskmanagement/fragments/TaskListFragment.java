package com.mobdeve.s13.g4.taskmanagement.fragments;

import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.models.*;
import com.mobdeve.s13.g4.taskmanagement.activities.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class TaskListFragment extends Fragment {

    private RecyclerView rvTaskList;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private ImageButton btnAddTask;

    public TaskListFragment() {}

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.fragment_tasklist, container, false);
        rvTaskList = view.findViewById(R.id.rvTaskList);
        btnAddTask = view.findViewById(R.id.btnAddTask);
        setupTaskListRecyclerView();
        setupAddTaskButton();
        return view;
    }

    private void setupTaskListRecyclerView() {
        rvTaskList.setLayoutManager( new LinearLayoutManager(getContext()) );

        // - Create temporary task data
        taskList = new ArrayList<>();

        taskList.add( new Task("Task 1", "Description 1", new Date()) );
        taskList.add( new Task("Task 2", "Description 2", new Date()) );
        taskList.add( new Task("Task 3", "Description 3", new Date()) );
        taskList.add( new Task("Task 4", "Description 4", new Date()) );
        taskList.add( new Task("Task 5", "Description 5", new Date()) );

        // - Create and set the task adapter
        taskAdapter = new TaskAdapter(taskList);
        rvTaskList.setAdapter(taskAdapter);
    }

    private void setupAddTaskButton() {
        btnAddTask.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent( getActivity(), AddTaskActivity.class );
                startActivity(intent);
            }
        });
    }

    // Task Adapter
    private class TaskAdapter extends RecyclerView.Adapter<TaskListViewHolder> {
        private List<Task> tasks;

        public TaskAdapter(List<Task> tasks) {
            this.tasks = tasks;
        }

        @NonNull
        @Override
        public TaskListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bubble_tasks, parent, false);
            return new TaskListViewHolder(view);
        }

        @Override
        public void onBindViewHolder( @NonNull TaskListViewHolder holder, int position ) {
            Task task = tasks.get(position);
            holder.bind(task);
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }
    }

    private class TaskListViewHolder extends RecyclerView.ViewHolder {
        private ImageView flagImageView;
        private TextView titleTextView;
        private TextView categoryTextView;
        private TextView deadlineTextView;
        private TextView descriptionTextView;

        public TaskListViewHolder( @NonNull View itemView ) {
            super(itemView);
            flagImageView = itemView.findViewById(R.id.flagImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            deadlineTextView = itemView.findViewById(R.id.deadlineTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }

        public void bind( Task task ) {
            titleTextView.setText(task.getTitle());

            if( task.getCategory() != null ) {
                categoryTextView.setText(task.getCategory().toString());
                categoryTextView.setVisibility(View.VISIBLE);
            } else {
                categoryTextView.setVisibility(View.GONE);
            }

            if( task.getDueDate() != null ) {
                deadlineTextView.setText("Task Deadline: " + task.getDueDate());
                deadlineTextView.setVisibility(View.VISIBLE);
            } else {
                deadlineTextView.setVisibility(View.GONE);
            }

            if( task.getDescription() != null ) {
                descriptionTextView.setText(task.getDescription());
                descriptionTextView.setVisibility(View.VISIBLE);
            } else {
                descriptionTextView.setVisibility(View.GONE);
            }

            flagImageView.setVisibility(View.GONE);
        }
    }
}