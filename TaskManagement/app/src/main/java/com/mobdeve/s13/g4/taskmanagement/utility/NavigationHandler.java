package com.mobdeve.s13.g4.taskmanagement.utility;


import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.activities.*;
import com.mobdeve.s13.g4.taskmanagement.fragments.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NavigationHandler {
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;

    public NavigationHandler(BottomNavigationView bottomNavigationView, FragmentManager fragmentManager) {
        this.bottomNavigationView = bottomNavigationView;
        this.fragmentManager = fragmentManager;
        setupNavigationClickListeners();
    }

    private void setupNavigationClickListeners() {
        bottomNavigationView.getBtnViewSidebar().setOnClickListener(v -> openSidebar());
        bottomNavigationView.getBtnViewHome().setOnClickListener(v -> showFragment(new HomeFragment()));
        bottomNavigationView.getBtnAddTask().setOnClickListener(v -> addTask());
        bottomNavigationView.getBtnViewCalendar().setOnClickListener(v -> showFragment(new CalendarFragment()));
        bottomNavigationView.getBtnViewProfile().setOnClickListener(v -> showFragment(new ProfileFragment()));
    }

    public void openSidebar() {
        // Implement the logic to open the sidebar modal
    }

    public void addTask() {
        // Implement the logic to add a new task
    }

    public void showFragment( Fragment fragment ) {
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }
}