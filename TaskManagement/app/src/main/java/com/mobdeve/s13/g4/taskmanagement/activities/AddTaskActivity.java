package com.mobdeve.s13.g4.taskmanagement.activities;

import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.database.DatabaseHandler;
import com.mobdeve.s13.g4.taskmanagement.fragments.*;
import com.mobdeve.s13.g4.taskmanagement.utility.*;
import com.mobdeve.s13.g4.taskmanagement.models.*;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    // - Task Header
    private EditText etTaskTitle;
    private EditText etTaskDescription;

    // - Task Details
    private TextView tvAssignCategory;
    private TextView tvAssignPriority;
    private ImageButton btnClearPriority;

    // - Task Due Date
    private Button tvAssignDueDate;


    /*|*******************************************************
                    Constructor Methods
    *********************************************************/
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        findAllViews();
        setupButtonsFunctionality();
    }

    private void findAllViews() {
        etTaskTitle = findViewById(R.id.etTaskTitle);
        etTaskDescription = findViewById(R.id.etTaskDescription);
        tvAssignCategory = findViewById(R.id.tvAssignCategory);
        tvAssignPriority = findViewById(R.id.tvAssignPriority);
        btnClearPriority = findViewById(R.id.btnClearPriority);
        tvAssignDueDate = findViewById(R.id.tvAssignDueDate);
    }

    private void setupButtonsFunctionality() {
        tvAssignCategory.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                openAssignCategoryDialog();
            }
        });

        tvAssignPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAssignPriorityDialog();
            }
        });

        btnClearPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAssignPriority.setText("Assign Priority");
            }
        });

        tvAssignDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAssignDateDialog();
            }
        });
    }

    /*|*******************************************************
                        Category Methods
    *********************************************************/
    private void openAssignCategoryDialog() {
        AssignCategoryDialog dialog = AssignCategoryDialog.newInstance();
        dialog.setOnCategorySelectedListener( new AssignCategoryDialog.OnCategorySelectedListener() {
            @Override
            public void onCategorySelected( Category category ) {
                tvAssignCategory.setText( category.getName() );
            }
        });
        dialog.show( getSupportFragmentManager(), "AssignCategoryDialog" );
    }

    /*|*******************************************************
                        Due Date Time Methods
    *********************************************************/
    private void openAssignDateDialog() {
        AssignDateDialog dialog = AssignDateDialog.newInstance();
        dialog.setOnDateSelectedListener( new AssignDateDialog.OnDateSelectedListener() {

            // - Update UI with the selected date
            @Override
            public void onDateSelected( Calendar selectedDate ) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
                String formattedDate = dateFormat.format(selectedDate.getTime());
                tvAssignDueDate.setText(formattedDate);
            }
        });
        dialog.show(getSupportFragmentManager(), "AssignDateDialog");
    }

    /*|*******************************************************
                        Priority Methods
    *********************************************************/
    private void openAssignPriorityDialog() {
        AssignPriorityDialog dialog = AssignPriorityDialog.newInstance();
        dialog.setOnPrioritySelectedListener(new AssignPriorityDialog.OnPrioritySelectedListener() {
            @Override
            public void onPrioritySelected(String priority) {
                tvAssignPriority.setText(priority);
            }
        });
        dialog.show(getSupportFragmentManager(), "AssignPriorityDialog");
    }

}