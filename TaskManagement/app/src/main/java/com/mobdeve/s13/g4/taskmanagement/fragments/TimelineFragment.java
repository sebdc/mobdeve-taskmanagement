package com.mobdeve.s13.g4.taskmanagement.fragments;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.adapters.*;
import com.mobdeve.s13.g4.taskmanagement.models.*;
import com.mobdeve.s13.g4.taskmanagement.database.*;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimelineFragment extends Fragment {

    private RecyclerView rvBubbleDates;
    private RecyclerView rvTaskList;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private BubbleDateAdapter bubbleDateAdapter;
    private ImageButton btnCalendar;

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

        // Create sample user and user data
        UserData userData = new UserData();
        userData.initTempData();
        userData.initTempData();

        // Get the task list from user data
        taskList = new ArrayList<>(userData.getTaskSet());

        // Create and set the task adapter
        taskAdapter = new TaskAdapter(taskList);
        rvTaskList.setAdapter(taskAdapter);
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
}