package com.mobdeve.s13.g4.taskmanagement.activities;

import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.database.DatabaseHandler;
import com.mobdeve.s13.g4.taskmanagement.fragments.*;
import com.mobdeve.s13.g4.taskmanagement.adapters.*;
import com.mobdeve.s13.g4.taskmanagement.models.*;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
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
    private Button tvAssignDueTime;

    // - Create Task
    private Button btnCreateTask;

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

    /*|*******************************************************
                   Initialize Methods
    *********************************************************/
    private void findAllViews() {
        etTaskTitle = findViewById(R.id.etTaskTitle);
        etTaskDescription = findViewById(R.id.etTaskDescription);
        tvAssignCategory = findViewById(R.id.tvAssignCategory);
        tvAssignPriority = findViewById(R.id.tvAssignPriority);
        btnClearPriority = findViewById(R.id.btnClearPriority);
        tvAssignDueDate = findViewById(R.id.tvAssignDueDate);
        tvAssignDueTime = findViewById(R.id.tvAssignDueTime);
        btnCreateTask = findViewById(R.id.btnCreateTask);
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

        tvAssignDueTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker();
            }
        });

        btnCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTask();
            }
        });
    }

    /*|*******************************************************
                        Create Task Methods
    *********************************************************/
    private void createTask() {
        String title = etTaskTitle.getText().toString().trim();
        String description = etTaskDescription.getText().toString().trim();
        String categoryName = tvAssignCategory.getText().toString().trim();
        String priorityLevel = tvAssignPriority.getText().toString().trim();

        if( title.isEmpty() ) {
            Toast.makeText(this, "Please enter a task title", Toast.LENGTH_SHORT).show();
            return;
        }

        // - Create new task if we have a title
        Task task = new Task(title);

        // - Add optional details
        if( !description.isEmpty() ) { task.setDescription( description ); }
        if( !priorityLevel.isEmpty() ) { task.setPriorityLevel( priorityLevel ); }

        // - Due date
        // - Due time

        if( !categoryName.isEmpty() ) {
            /*
                - In category dialog, when a category is selected, we somehow need its ID
                - When we get the ID, we perform dbHandler.getCategoryById();
                - We use it for task category
            */
        }

        DatabaseHandler dbHandler = new DatabaseHandler(this);
        dbHandler.insertTask(task);
        Toast.makeText(this, "Task inserted!", Toast.LENGTH_SHORT).show();

        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
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

    private void openTimePicker() {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String amPm = (hourOfDay < 12) ? "AM" : "PM";
                        int hour = (hourOfDay == 0) ? 12 : ((hourOfDay > 12) ? hourOfDay - 12 : hourOfDay);
                        String formattedTime = String.format("%02d:%02d %s", hour, minute, amPm);
                        tvAssignDueTime.setText(formattedTime);
                    }
                }, hour, minute, false);

        timePickerDialog.show();
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