package com.mobdeve.s13.g4.taskmanagement.activities;

import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.database.DatabaseHandler;
import com.mobdeve.s13.g4.taskmanagement.fragments.AssignCategoryDialog;
import com.mobdeve.s13.g4.taskmanagement.fragments.AssignDateDialog;
import com.mobdeve.s13.g4.taskmanagement.fragments.AssignPriorityDialog;
import com.mobdeve.s13.g4.taskmanagement.models.*;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ViewTaskActivity extends AppCompatActivity {

    private Task selectedTask;

    // - Task Header
    private EditText etTaskTitle;
    private EditText etTaskDescription;
    private ImageButton btnBack;

    // - Task Details
    private TextView tvAssignCategory;
    private TextView tvAssignPriority;
    private ImageButton btnClearPriority;

    // - Task Due Date
    private Button btnAssignDueDate;
    private Button btnAssignDueTime;

    // - Update Task
    private Button btnUpdateTask;
    private ImageButton btnDeleteTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        findAllViews();
        setupButtonsFunctionality();

        // - Get the selected task from the intent extras
        selectedTask = (Task) getIntent().getSerializableExtra("task");
        updateTaskViewDetails(selectedTask);
    }

    /*|*******************************************************
                        Initialize Methods
    *********************************************************/
    private void findAllViews() {
        etTaskTitle = findViewById(R.id.etTaskTitle);
        etTaskDescription = findViewById(R.id.etTaskDescription);
        btnBack = findViewById(R.id.btnBack);

        tvAssignCategory = findViewById(R.id.tvAssignCategory);
        tvAssignPriority = findViewById(R.id.tvAssignPriority);
        btnClearPriority = findViewById(R.id.btnClearPriority);

        btnAssignDueDate = findViewById(R.id.btnAssignDueDate);
        btnAssignDueTime = findViewById(R.id.btnAssignDueTime);

        btnUpdateTask = findViewById(R.id.btnUpdateTask);
        btnDeleteTask = findViewById(R.id.btnDeleteTask);
    }

    private void setupButtonsFunctionality() {
        btnBack.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                onBackPressed();
            }
        });

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

        btnAssignDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAssignDateDialog();
            }
        });

        btnAssignDueTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker();
            }
        });

        btnUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTask();
            }
        });

        btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTask();
            }
        });
    }


    /*|*******************************************************
                     Update/Delete Task Methods
    *********************************************************/
    private void deleteTask() {
        DatabaseHandler dbHandler = new DatabaseHandler(this);
        dbHandler.deleteTask(selectedTask);
        Toast.makeText(this, "Task deleted!", Toast.LENGTH_SHORT).show();
        updateTaskViewDetails(selectedTask);
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void updateTask() {
        String title = etTaskTitle.getText().toString().trim();
        String description = etTaskDescription.getText().toString().trim();
        String categoryName = tvAssignCategory.getText().toString().trim();
        String priorityLevel = tvAssignPriority.getText().toString().trim();
        String dueDate = btnAssignDueDate.getText().toString().trim();
        String dueTime = btnAssignDueTime.getText().toString().trim();

        if( title.isEmpty() ) {
            Toast.makeText(this, "Please enter a task title", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHandler dbHandler = new DatabaseHandler(this);
        selectedTask.setTitle(title);

        // - Add optional details
        if( !description.isEmpty() ) { selectedTask.setDescription( description ); }
        if( !priorityLevel.isEmpty() ) { selectedTask.setPriorityLevel( priorityLevel ); }

        if( !categoryName.isEmpty() ) {
            Category category = dbHandler.getCategoryByName(categoryName);
            selectedTask.setCategory( category );
        }

        if( !priorityLevel.isEmpty() ) {
            selectedTask.setPriorityLevel(priorityLevel);
        }

        if( !dueDate.isEmpty() && !dueDate.equals("Due Date") ) {
            selectedTask.setDueDate(dueDate);
        }

        if( !dueTime.isEmpty() ) {
            selectedTask.setDueTime(dueTime);
        }

        dbHandler.updateTask(selectedTask);
        Toast.makeText(this, "Task updated!", Toast.LENGTH_SHORT).show();
        updateTaskViewDetails(selectedTask);
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
    }

    private void updateTaskViewDetails( Task task ) {

        boolean doesTaskTitleExist = task.getTitle() != null;
        boolean doesTaskDescriptionExist = task.getTitle() != null;
        boolean doesTaskCategoryExist = task.getCategory() != null && task.getCategory().getName() != null;
        boolean doesDueDateExist = task.getDueDate() != null;
        boolean doesDueTimeExist = task.getDueTime() != null;
        boolean doesPriorityLevelExist = task.getPriorityLevel() != null;

        if( doesTaskTitleExist ) {
            etTaskTitle.setText(task.getTitle());
        }

        if( doesTaskDescriptionExist ) {
            etTaskDescription.setText(task.getDescription());
        }

        if( doesTaskCategoryExist ) {
            tvAssignCategory.setText(task.getCategory().getName());
        }

        if( doesDueDateExist ) {
            btnAssignDueDate.setText(task.getDueDate());
        }

        if( doesDueTimeExist ) {
            btnAssignDueTime.setText(task.getDueTime());
        }

        if( doesPriorityLevelExist ) {
            tvAssignPriority.setText(task.getPriorityLevel());
        }
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
                btnAssignDueDate.setText(formattedDate);
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
                        btnAssignDueTime.setText(formattedTime);
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