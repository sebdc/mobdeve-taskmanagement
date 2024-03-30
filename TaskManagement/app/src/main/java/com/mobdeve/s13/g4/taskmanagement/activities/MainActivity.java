package com.mobdeve.s13.g4.taskmanagement.activities;

import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.database.DatabaseHandler;
import com.mobdeve.s13.g4.taskmanagement.fragments.*;
import com.mobdeve.s13.g4.taskmanagement.utility.*;
import com.mobdeve.s13.g4.taskmanagement.models.*;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout fragmentContainer;
    private DatabaseHandler databaseHandler;
    private NavigationHandler navigationHandler;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHandler = new DatabaseHandler(this);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fragmentContainer = findViewById(R.id.fragmentContainer);

        navigationHandler = new NavigationHandler(bottomNavigationView, getSupportFragmentManager());
        navigationHandler.showFragment(new HomeFragment()); // - show the default fragment
    }
}