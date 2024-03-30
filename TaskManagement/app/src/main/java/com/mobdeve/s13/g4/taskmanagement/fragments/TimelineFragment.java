package com.mobdeve.s13.g4.taskmanagement.fragments;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.activities.ViewTaskActivity;
import com.mobdeve.s13.g4.taskmanagement.adapters.*;
import com.mobdeve.s13.g4.taskmanagement.models.*;
import com.mobdeve.s13.g4.taskmanagement.database.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimelineFragment extends Fragment {

    private RecyclerView rvBubbleDates;
    private RecyclerView rvTaskList;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private BubbleDateAdapter bubbleDateAdapter;
    private ImageButton btnCalendar;

    private ActivityResultLauncher<Intent> updateTaskLauncher;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);

        // - Initialize and set up the bubble date RecyclerView
        rvBubbleDates = view.findViewById(R.id.rvBubbleDates);
        setupBubbleDateRecyclerView();

        // - Initialize and set up the task list RecyclerView
        rvTaskList = view.findViewById(R.id.rvTaskList);
        setupTaskListRecyclerView();

        btnCalendar = view.findViewById(R.id.btnCalendar);
        btnCalendar.setOnClickListener(v -> openCalendar());

        setupTaskLauncher();

        // Inflate the layout for this fragment
        return view;
    }

    private void setupBubbleDateRecyclerView() {
        rvBubbleDates.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        List<Calendar> dates = DateDataHelper.generateDates();
        bubbleDateAdapter = new BubbleDateAdapter(dates, date -> {
            // - Handle the selected date here
            // - Update your UI or perform any other actions
        });
        rvBubbleDates.setAdapter(bubbleDateAdapter);
    }

    private void setupTaskListRecyclerView() {
        rvTaskList.setLayoutManager(new LinearLayoutManager(getContext()));

        DatabaseHandler dbHandler = new DatabaseHandler(getContext());
        taskList = dbHandler.getAllTasks();

        // Create and set the task adapter
        taskAdapter = new TaskAdapter(taskList);
        rvTaskList.setAdapter(taskAdapter);
    }

    public void refreshTaskList() {
        DatabaseHandler dbHandler = new DatabaseHandler(getContext());
        taskList.clear();
        taskList.addAll(dbHandler.getAllTasks());
        taskAdapter.notifyDataSetChanged();
    }


    private void openCalendar() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a date");
        MaterialDatePicker<Long> picker = builder.build();

        picker.show(getParentFragmentManager(), "DatePicker");
        picker.addOnPositiveButtonClickListener(selectedDate -> {
            // Handle the selected date
            // You can update your UI or perform any other actions here
        });
    }

    private void setupTaskLauncher() {
        updateTaskLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if( result.getResultCode() == Activity.RESULT_OK ) {
                        refreshTaskList();
                    }
                });
    }

    /*|*******************************************************
                       Task Adapter
    *********************************************************/
    private class TaskAdapter extends RecyclerView.Adapter<TaskListViewHolder> {
        private List<Task> taskList;

        public TaskAdapter(List<Task> taskList) {
            this.taskList = taskList;
        }

        @NonNull
        @Override
        public TaskListViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeline_task, parent, false);
            return new TaskListViewHolder(view);

        }

        @Override
        public void onBindViewHolder( @NonNull TaskListViewHolder holder, int position ) {
            Task task = taskList.get(position);
            holder.bind(task);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
                intent.putExtra("task", task);
                updateTaskLauncher.launch(intent);
            });
        }

        @Override
        public int getItemCount() {
            return taskList.size();
        }

        private String formatDate(Date date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
            return dateFormat.format(date);
        }
    }

    private class TaskListViewHolder extends RecyclerView.ViewHolder {

        TextView taskDueDate;
        TextView taskTitle;
        TextView taskDueDateTime;
        TextView taskCategory;
        TextView taskPriority;

        public TaskListViewHolder(@NonNull View itemView) {
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

            /*
                        titleTextView.setText(task.getTitle());

            if( task.getCategory() != null ) {
                categoryTextView.setText(task.getCategory().getName());
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

             */
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
}