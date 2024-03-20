package com.mobdeve.s13.g4.taskmanagement.activities;

import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.adapters.*;
import com.mobdeve.s13.g4.taskmanagement.models.*;
import com.mobdeve.s13.g4.taskmanagement.viewholders.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ViewTaskActivity extends AppCompatActivity {

    private ImageButton backButton;
    private EditText taskTitleEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        backButton = findViewById(R.id.backButton);
        taskTitleEditText = findViewById(R.id.taskTitleEditText);

        // Get the selected task from the intent extras
        Task selectedTask = getIntent().getParcelableExtra("task");

        // Set the task title in the EditText
        if (selectedTask != null) {
            taskTitleEditText.setText(selectedTask.getTitle());
        }

        // Set click listener for the back button
        backButton.setOnClickListener(v -> onBackPressed());
    }
}