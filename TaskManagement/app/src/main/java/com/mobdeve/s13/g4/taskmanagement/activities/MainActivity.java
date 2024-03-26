package com.mobdeve.s13.g4.taskmanagement.activities;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.adapters.*;
import com.mobdeve.s13.g4.taskmanagement.fragments.CalendarFragment;
import com.mobdeve.s13.g4.taskmanagement.fragments.HomeFragment;
import com.mobdeve.s13.g4.taskmanagement.fragments.ProfileFragment;
import com.mobdeve.s13.g4.taskmanagement.models.*;
import com.mobdeve.s13.g4.taskmanagement.database.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fragmentContainer = findViewById(R.id.fragmentContainer);

        // Set up click listeners for the bottom navigation buttons
        bottomNavigationView.getBtnViewSidebar().setOnClickListener(v -> openSidebar());
        bottomNavigationView.getBtnViewHome().setOnClickListener(v -> showFragment(new HomeFragment()));
        bottomNavigationView.getBtnAddTask().setOnClickListener(v -> addTask());
        bottomNavigationView.getBtnViewCalendar().setOnClickListener(v -> showFragment(new CalendarFragment()));
        bottomNavigationView.getBtnViewProfile().setOnClickListener(v -> showFragment(new ProfileFragment()));

        // Show the default fragment
        showFragment(new CalendarFragment());
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

    private void showFragment( Fragment fragment ) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }
}