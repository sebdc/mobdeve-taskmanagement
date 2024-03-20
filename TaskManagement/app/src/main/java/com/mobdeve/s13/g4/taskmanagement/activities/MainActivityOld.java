package com.mobdeve.s13.g4.taskmanagement.activities;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.activities.BottomNavigationView;
import com.mobdeve.s13.g4.taskmanagement.adapters.*;
import com.mobdeve.s13.g4.taskmanagement.models.*;
import com.mobdeve.s13.g4.taskmanagement.database.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivityOld extends AppCompatActivity {

    private UserData userData;
    private RecyclerView rvBubbleDates;
    private RecyclerView rvTaskList;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private BubbleDateAdapter bubbleDateAdapter;
    private ImageButton btnCalendar;

    private BottomNavigationView bottomNavigationView;
    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);

        /*
        ImageButton btnViewSidebar = findViewById(R.id.btnViewSidebar);
        ImageButton btnViewHome = findViewById(R.id.btnViewHome);
        ImageButton btnAddTask = findViewById(R.id.btnAddTask);
        ImageButton btnViewCalendar = findViewById(R.id.btnViewCalendar);
        ImageButton btnViewProfile = findViewById(R.id.btnViewProfile);

        btnViewSidebar.setOnClickListener(v -> openSidebar());
        btnViewHome.setOnClickListener(v -> navigateToHome());
        btnAddTask.setOnClickListener(v -> addTask());
        btnViewCalendar.setOnClickListener(v -> navigateToCalendar());
        btnViewProfile.setOnClickListener(v -> navigateToProfile());

        // Initialize and set up the bubble date RecyclerView
        rvBubbleDates = findViewById(R.id.rvBubbleDates);
        rvBubbleDates.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<Calendar> dates = DateDataHelper.generateDates();
        bubbleDateAdapter = new BubbleDateAdapter(dates, date -> {
            // Handle the selected date here
            // Update your UI or perform any other actions
        });
        rvBubbleDates.setAdapter(bubbleDateAdapter);

        // Initialize and set up the task list RecyclerView
        rvTaskList = findViewById(R.id.rvTaskList);
        rvTaskList.setLayoutManager(new LinearLayoutManager(this));

        // Create sample user and user data
        UserData userData = new UserData();
        userData.initTempData();
        userData.initTempData();

        // Get the task list from user data
        taskList = new ArrayList<>(userData.getTaskSet());

        // Create and set the task adapter
        taskAdapter = new TaskAdapter(taskList);
        rvTaskList.setAdapter(taskAdapter);

        btnCalendar = findViewById(R.id.btnCalendar);
        btnCalendar.setOnClickListener(v -> openCalendar());

         */
    }

    private void openCalendar() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a date");
        MaterialDatePicker<Long> picker = builder.build();

        picker.show(getSupportFragmentManager(), "DatePicker");
        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selectedDate) {
                // Handle the selected date
                // You can update your UI or perform any other actions here
            }
        });
    }

    private void openSidebar() {
        // Implement the logic to open the sidebar modal
        // You can use a library like DrawerLayout or create a custom modal
    }
/*
    private void navigateToHome() {
        Intent intent = new Intent(this, HomeFragment.class);
        startActivity(intent);
    }

    private void addTask() {
        // Implement the logic to add a new task
    }

    private void navigateToCalendar() {
        Intent intent = new Intent(this, CalendarFragment.class);
        startActivity(intent);
    }

    private void navigateToProfile() {
        Intent intent = new Intent(this, ProfileFragment.class);
        startActivity(intent);
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
*/
}