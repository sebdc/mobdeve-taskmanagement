package com.mobdeve.s13.g4.taskmanagement.activities;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.adapters.*;
import com.mobdeve.s13.g4.taskmanagement.models.*;
import com.mobdeve.s13.g4.taskmanagement.database.*;
import com.mobdeve.s13.g4.taskmanagement.viewholders.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TreeSet;

public class BottomNavigationView extends LinearLayout {

    private ImageButton btnViewSidebar;
    private ImageButton btnViewHome;
    private ImageButton btnAddTask;
    private ImageButton btnViewCalendar;
    private ImageButton btnViewProfile;

    public BottomNavigationView(Context context) {
        super(context);
        initView(context);
    }

    public BottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bottom_navigation, this, true);

        btnViewSidebar = view.findViewById(R.id.btnViewSidebar);
        btnViewHome = view.findViewById(R.id.btnViewHome);
        btnAddTask = view.findViewById(R.id.btnAddTask);
        btnViewCalendar = view.findViewById(R.id.btnViewCalendar);
        btnViewProfile = view.findViewById(R.id.btnViewProfile);

        // Set up click listeners for the bottom navigation buttons
        // ...
    }

    private void openSidebar() {
        // Implement the logic to open the sidebar modal
        // You can use a library like DrawerLayout or create a custom modal
    }

    public Intent navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        return intent;
    }

    public Intent addTask() {
        // Implement the logic to add a new task
    }

    public Intent navigateToCalendar() {
        Intent intent = new Intent(this, CalendarActivity.class);
        return intent;
    }

    public Intent navigateToProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        return intent;
    }
}