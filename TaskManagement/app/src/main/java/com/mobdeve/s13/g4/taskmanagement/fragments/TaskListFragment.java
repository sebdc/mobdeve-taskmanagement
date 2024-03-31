package com.mobdeve.s13.g4.taskmanagement.fragments;

import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.database.DatabaseHandler;
import com.mobdeve.s13.g4.taskmanagement.models.*;
import com.mobdeve.s13.g4.taskmanagement.activities.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class TaskListFragment extends Fragment {

    private RecyclerView rvTaskList;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private ImageButton btnAddTask;

    private ActivityResultLauncher<Intent> addTaskLauncher;
    private ActivityResultLauncher<Intent> updateTaskLauncher;

    /*|*******************************************************
                         Constructor Methods
    *********************************************************/
    public TaskListFragment() {}

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.fragment_tasklist, container, false);
        rvTaskList = view.findViewById(R.id.rvTaskList);
        btnAddTask = view.findViewById(R.id.btnAddTask);
        setupTaskListRecyclerView();
        setupAddTaskButton();
        setupTaskLauncher();
        return view;
    }

    private void setupTaskListRecyclerView() {
        rvTaskList.setLayoutManager( new LinearLayoutManager(getContext()) );

        // - Create temporary task data
        DatabaseHandler dbHandler = new DatabaseHandler(getContext());
        taskList = dbHandler.getAllTasks();

        // - Create and set the task adapter
        taskAdapter = new TaskAdapter(taskList);
        rvTaskList.setAdapter(taskAdapter);
    }

    private void setupAddTaskButton() {
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTaskActivity.class);
                addTaskLauncher.launch(intent);
            }
        });
    }

    private void setupTaskLauncher() {
        addTaskLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if( result.getResultCode() == Activity.RESULT_OK ) {
                        refreshTaskList();
                    }
                });

        updateTaskLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if( result.getResultCode() == Activity.RESULT_OK ) {
                        refreshTaskList();
                    }
                });
    }

    public void refreshTaskList() {
        DatabaseHandler dbHandler = new DatabaseHandler(getContext());
        taskList.clear();
        taskList.addAll(dbHandler.getAllTasks());
        taskAdapter.notifyDataSetChanged();
    }

    /*|*******************************************************
                           Task Adapter
    *********************************************************/
    private class TaskAdapter extends RecyclerView.Adapter<TaskListViewHolder> {
        private List<Task> tasks;

        public TaskAdapter(List<Task> tasks) {
            this.tasks = tasks;
        }

        @NonNull
        @Override
        public TaskListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tasklist_tasks, parent, false);
            return new TaskListViewHolder(view);
        }

        @Override
        public void onBindViewHolder( @NonNull TaskListViewHolder holder, int position ) {
            Task task = tasks.get(position);
            holder.bind(task);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
                intent.putExtra("task", task);
                updateTaskLauncher.launch(intent);
            });
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }
    }


    /*|*******************************************************
                      Task List View Holder
    *********************************************************/
    private class TaskListViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFlagIcon;
        private TextView titleTextView;
        private TextView tvCategoryTag;
        private TextView deadlineTextView;
        private TextView descriptionTextView;

        public TaskListViewHolder( @NonNull View itemView ) {
            super(itemView);
            ivFlagIcon = itemView.findViewById(R.id.ivFlagIcon);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            tvCategoryTag = itemView.findViewById(R.id.tvCategoryTag);
            deadlineTextView = itemView.findViewById(R.id.deadlineTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }

        public void bind( Task task ) {
            titleTextView.setText(task.getTitle());

            if (task.getCategory() != null) {
                Category category = task.getCategory();

                tvCategoryTag.setText(task.getCategory().getName());
                tvCategoryTag.setVisibility(View.VISIBLE);

                String categoryColor = category.getMainColor();
                if (categoryColor != null && !categoryColor.isEmpty()) {
                    try {
                        int color = Color.parseColor(categoryColor);
                        tvCategoryTag.setTextColor(color);

                        // - 10% opacity
                        int backgroundColorWithOpacity = ColorUtils.setAlphaComponent(color, (int) (255 * 0.1));
                        tvCategoryTag.getBackground().setColorFilter(backgroundColorWithOpacity, PorterDuff.Mode.SRC_IN);
                    } catch (IllegalArgumentException e) {
                        // Handle invalid color format
                        // You can set a default color or take appropriate action
                        tvCategoryTag.setTextColor(Color.BLACK);
                        tvCategoryTag.getBackground().setColorFilter(null);
                    }
                }
            } else {
                    tvCategoryTag.setVisibility(View.GONE);
                }

            if( task.getDueDate() != null ) {
                deadlineTextView.setText("Deadline: " + task.getDueDate());
                deadlineTextView.setVisibility(View.VISIBLE);
            } else {
                deadlineTextView.setVisibility(View.GONE);
            }

            if (task.getDescription() != null) {
                descriptionTextView.setText(task.getDescription());
                descriptionTextView.setVisibility(View.VISIBLE);
            } else {
                descriptionTextView.setVisibility(View.GONE);
            }

            if (task.getPriorityLevel() != null) {
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
                    ivFlagIcon.setColorFilter(ContextCompat.getColor(getContext(), R.color.high), PorterDuff.Mode.SRC_IN);
                };
                break;
                case "Medium": {
                    ivFlagIcon.setVisibility(View.VISIBLE);
                    ivFlagIcon.setColorFilter(ContextCompat.getColor(getContext(), R.color.medium), PorterDuff.Mode.SRC_IN);
                };
                break;
                case "Low": {
                    ivFlagIcon.setVisibility(View.VISIBLE);
                    ivFlagIcon.setColorFilter(ContextCompat.getColor(getContext(), R.color.low), PorterDuff.Mode.SRC_IN);
                };
                break;
                default: {
                    ivFlagIcon.setVisibility(View.GONE);
                };
            }
        }
    }
}
