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

    // - Intent Object
    private Task selectedTask;

    // - Task Details
    private EditText etTaskTitle;
    private EditText etTaskDescription;
    private TextView tvAssignCategory;
    private TextView tvAssignPriority;
    private Button btnAssignDueDate;
    private Button btnAssignDueTime;

    // - Clear Buttons
    private ImageButton btnBack;
    private ImageButton btnClearCategory;
    private ImageButton btnClearPriority;
    private ImageButton btnClearDueDate;

    // - Default values
    private static final String CATEGORY_DEFAULT = "Assign category";
    private static final String PRIORITY_DEFAULT = "Assign priority";
    private static final String DUE_DATE_DEFAULT = "Due date";
    private static final String DUE_TIME_DEFAULT = "Add time";

    // - Update Task
    private Button btnUpdateTask;
    private ImageButton btnDeleteTask;


    /*|*******************************************************
                        Constructor Methods
    *********************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        findAllViews();
        setupButtonsFunctionality();

        // - Get the selected task from the intent extras
        selectedTask = (Task) getIntent().getSerializableExtra("task");
        updateTaskViewDetails(selectedTask);
        resetClearButtons();
    }

    /*|*******************************************************
                        Initialize Methods
    *********************************************************/
    private void findAllViews() {
        etTaskTitle = findViewById(R.id.etTaskTitle);
        etTaskDescription = findViewById(R.id.etTaskDescription);
        btnBack = findViewById(R.id.btnBack);

        tvAssignCategory = findViewById(R.id.tvAssignCategory);
        btnClearCategory = findViewById(R.id.btnClearCategory);

        tvAssignPriority = findViewById(R.id.tvAssignPriority);
        btnClearPriority = findViewById(R.id.btnClearPriority);

        btnAssignDueDate = findViewById(R.id.btnAssignDueDate);
        btnAssignDueTime = findViewById(R.id.btnAssignDueTime);

        btnUpdateTask = findViewById(R.id.btnUpdateTask);
        btnDeleteTask = findViewById(R.id.btnDeleteTask);
        btnClearDueDate = findViewById(R.id.btnClearDueDate);
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

        btnClearCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAssignCategory.setText(CATEGORY_DEFAULT);
                btnClearCategory.setVisibility(View.GONE);
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
                tvAssignPriority.setText(PRIORITY_DEFAULT);
            }
        });

        btnClearPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAssignPriority.setText(PRIORITY_DEFAULT);
                btnClearPriority.setVisibility(View.GONE);
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

        btnClearDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAssignDueDate.setText(DUE_DATE_DEFAULT);
                btnAssignDueTime.setText(DUE_TIME_DEFAULT);
                btnClearDueDate.setVisibility(View.GONE);
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

        selectedTask.setDueDate(dueDate);
        Toast.makeText(this, selectedTask.getDueDate(), Toast.LENGTH_SHORT).show();

        if( title.isEmpty() ) {
            Toast.makeText(this, "Please enter a task title", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHandler dbHandler = new DatabaseHandler(this);
        selectedTask.setTitle(title);

        // - Add optional details
        selectedTask.setDescription( description );
        selectedTask.setPriorityLevel( priorityLevel );

        if( !categoryName.isEmpty() ) {
            Category category = dbHandler.getCategoryByName(categoryName);
            selectedTask.setCategory( category );
        }

        selectedTask.setDueDate(dueDate);
        selectedTask.setDueTime(dueTime);


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
                toggleClearCategory();
            }
        });
        dialog.show( getSupportFragmentManager(), "AssignCategoryDialog" );
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
                toggleClearPriority();
            }
        });
        dialog.show(getSupportFragmentManager(), "AssignPriorityDialog");
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
                    toggleClearDueDate();

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
                        toggleClearDueTime();
                    }
                }, hour, minute, false);

        timePickerDialog.show();
    }

    /*|*******************************************************
                    Reset Details Button
    *********************************************************/
    private void resetClearButtons() {
        toggleClearCategory();
        toggleClearPriority();
        toggleClearDueDateTime();
    }

    private void toggleClearCategory() {
        String categoryName = tvAssignCategory.getText().toString().trim();
        if( categoryName.equals("None") ) {
            tvAssignCategory.setText(CATEGORY_DEFAULT);
            btnClearCategory.setVisibility(View.GONE);
        } else if( categoryName.equals(CATEGORY_DEFAULT) ) {
            btnClearCategory.setVisibility(View.GONE);
        } else {
            btnClearCategory.setVisibility(View.VISIBLE);
        }
    }

    private void toggleClearPriority() {
        String priority = tvAssignPriority.getText().toString().trim();
        if( priority.equals("None") ) {
            tvAssignPriority.setText(PRIORITY_DEFAULT);
            btnClearPriority.setVisibility(View.GONE);
        } else if( priority.equals(PRIORITY_DEFAULT) ) {
            btnClearPriority.setVisibility(View.GONE);
        } else {
            btnClearPriority.setVisibility(View.VISIBLE);
        }
    }

    private void toggleClearDueDateTime() {
        String dueDate = btnAssignDueDate.getText().toString().trim();
        String dueTime = btnAssignDueTime.getText().toString().trim();

        if( dueDate.equals(DUE_DATE_DEFAULT) && dueTime.equals(DUE_TIME_DEFAULT) ) {
            btnClearDueDate.setVisibility(View.GONE);
        } else {
            btnClearDueDate.setVisibility(View.VISIBLE);
        }
    }

    private void toggleClearDueDate() {
        String dueDate = btnAssignDueDate.getText().toString().trim();
        if( dueDate.equals(DUE_DATE_DEFAULT) ) {
            btnClearDueDate.setVisibility(View.GONE);
        } else {
            btnClearDueDate.setVisibility(View.VISIBLE);
        }
    }

    private void toggleClearDueTime() {
        String dueTime = btnAssignDueTime.getText().toString().trim();
        if( dueTime.equals(DUE_TIME_DEFAULT) ) {
            btnClearDueDate.setVisibility(View.GONE);
        } else {
            btnClearDueDate.setVisibility(View.VISIBLE);
        }
    }
}