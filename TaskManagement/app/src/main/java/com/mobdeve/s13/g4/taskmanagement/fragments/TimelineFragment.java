package com.mobdeve.s13.g4.taskmanagement.fragments;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.activities.AddTaskActivity;
import com.mobdeve.s13.g4.taskmanagement.activities.ViewTaskActivity;
import com.mobdeve.s13.g4.taskmanagement.adapters.*;
import com.mobdeve.s13.g4.taskmanagement.models.*;
import com.mobdeve.s13.g4.taskmanagement.database.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

public class TimelineFragment extends Fragment implements TimelineTaskAdapter.OnTaskClickListener {

    // - Attributes
    private Calendar selectedDate;
    private List<Task> taskList;
    private Map<Integer, List<Task>> dayTaskMap;
    private ActivityResultLauncher<Intent> addTaskLauncher;
    private ActivityResultLauncher<Intent> updateTaskLauncher;

    // - XML Attributes
    private TextView tvHeaderTitle;
    private ImageButton btnCalendar;
    private RecyclerView rvTaskList;
    private TimelineDayAdapter dayAdapter;
    private RecyclerView rvBubbleDates;
    private BubbleDateAdapter bubbleDateAdapter;
    private ImageButton btnAddTask;

    /*|*******************************************************
                        Constructor Methods
    *********************************************************/
    @Override
    public void onTaskClick(Task task) {
        Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
        intent.putExtra("task", task);
        updateTaskLauncher.launch(intent);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        findAllViews(view);
        selectedDate = Calendar.getInstance();

        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM yyyy", Locale.getDefault());
        String monthYear = monthFormat.format(selectedDate.getTime());
        tvHeaderTitle.setText(monthYear);

        // - Initialize and set up the bubble date RecyclerView
        rvBubbleDates = view.findViewById(R.id.rvBubbleDates);
        setupBubbleDateRecyclerView();
        updateBubbleDateRecyclerView(selectedDate);

        // - Initialize and set up the task list RecyclerView
        rvTaskList = view.findViewById(R.id.rvTaskList);
        setupTaskListRecyclerView();

        btnCalendar = view.findViewById(R.id.btnCalendar);
        btnCalendar.setOnClickListener(v -> openCalendar());

        setupAddTaskButton();
        setupTaskLauncher();

        // - Inflate the layout for this fragment
        return view;
    }

    /*|*******************************************************
                      Initialization Methods
    *********************************************************/
    private void findAllViews( View view ) {
        tvHeaderTitle = view.findViewById(R.id.tvHeaderTitle);
        btnAddTask = view.findViewById(R.id.btnAddTask);
    }

    private void setupBubbleDateRecyclerView() {
        rvBubbleDates.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        List<Calendar> dates = DateDataHelper.generateDatesForMonth(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH));
        bubbleDateAdapter = new BubbleDateAdapter(dates, (date, dayNumber) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(Calendar.YEAR, date.get(Calendar.YEAR));
            selectedDate.set(Calendar.MONTH, date.get(Calendar.MONTH));
            selectedDate.set(Calendar.DAY_OF_MONTH, dayNumber);
            updateSelectedDate(selectedDate);
        });
        rvBubbleDates.setAdapter(bubbleDateAdapter);
    }

    private void setupTaskListRecyclerView() {
        rvTaskList.setLayoutManager(new LinearLayoutManager(getContext()));

        // - Generate the list of days for the selected month
        List<Calendar> dayList = generateDaysForMonth(selectedDate);

        // - Get all tasks for the selected month
        DatabaseHandler dbHandler = new DatabaseHandler(getContext());
        initializeDayTaskMap();

        dayAdapter = new TimelineDayAdapter(dayList, dayTaskMap, this);
        rvTaskList.setAdapter(dayAdapter);
    }

    private void setupTaskLauncher() {
        addTaskLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if( result.getResultCode() == Activity.RESULT_OK ) {
                        dayAdapter.notifyDataSetChanged();
                        refreshTaskListRecyclerView();
                    }
                });

        updateTaskLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if( result.getResultCode() == Activity.RESULT_OK ) {
                        dayAdapter.notifyDataSetChanged();
                        refreshTaskListRecyclerView();
                    }
                });
    }

    private void setupAddTaskButton() {
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTaskActivity.class);
                addTaskLauncher.launch(intent);
            }
        });
    }

    private void initializeDayTaskMap() {
        // - Get all tasks for the selected month
        DatabaseHandler dbHandler = new DatabaseHandler(getContext());
        List<Task> tasksForMonth = dbHandler.getAllTasksByMonth(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH));

        // - Create a map to store tasks for each day
        dayTaskMap = new HashMap<>();
        for( Task task : tasksForMonth ) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
            String dueDateString = task.getDueDate();
            Calendar taskDueDateCalendar = Calendar.getInstance();

            try {
                Date dueDate = dateFormat.parse(dueDateString);
                taskDueDateCalendar.setTime(dueDate);
                int dayOfMonth = taskDueDateCalendar.get(Calendar.DAY_OF_MONTH);
                if( !dayTaskMap.containsKey(dayOfMonth) ) {
                    dayTaskMap.put(dayOfMonth, new ArrayList<>());
                    Log.d("dayTaskMapInitialization", "Initialization: " + dayOfMonth + " " + task.getTitle() );
                }
                dayTaskMap.get(dayOfMonth).add(task);
            } catch( ParseException e ) {
                e.printStackTrace();
            }
        }
    }

    private void scrollToSelectedDate() {
        int position = dayAdapter.getPositionForDate(selectedDate);
        if( position != -1 ) {
            rvTaskList.smoothScrollToPosition(position);
        }
    }

    /*|*******************************************************
                  Header Title and Calendar Button
    *********************************************************/
    private void updateHeaderTitle() {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM yyyy", Locale.getDefault());
        String monthYear = monthFormat.format(selectedDate.getTime());
        tvHeaderTitle.setText(monthYear);
    }

    private void openCalendar() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a date");
        builder.setSelection(selectedDate.getTimeInMillis());

        MaterialDatePicker<Long> picker = builder.build();
        picker.addOnPositiveButtonClickListener(selectedTimeInMillis -> {
            selectedDate.setTimeInMillis(selectedTimeInMillis);
            updateSelectedDate(selectedDate);
            refreshTaskListRecyclerView();
        });

        picker.show(getParentFragmentManager(), "DatePicker");
    }

    private void updateCalendar() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a date");
        builder.setSelection(selectedDate.getTimeInMillis());

        MaterialDatePicker<Long> picker = builder.build();
        picker.addOnPositiveButtonClickListener(selectedTimeInMillis -> {
            selectedDate.setTimeInMillis(selectedTimeInMillis);
        });
    }

    /*|*******************************************************
                     BubbleDate RecyclerView
    *********************************************************/
    private void updateSelectedDate( Calendar newSelectedDate ) {
        selectedDate = newSelectedDate;
        updateBubbleDateRecyclerView(selectedDate);
        updateCalendar();
        updateHeaderTitle();
        scrollToSelectedDate();
        refreshTaskListRecyclerView();
    }

    private void updateBubbleDateRecyclerView( Calendar calendar ) {
        List<Calendar> dates = DateDataHelper.generateDatesForMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
        bubbleDateAdapter.setDates(dates);
        bubbleDateAdapter.setSelectedDate(selectedDate);
    }

    /*|*******************************************************
                         Task List
    *********************************************************/
    private List<Calendar> generateDaysForMonth( Calendar selectedDate ) {
        List<Calendar> dayList = new ArrayList<>();

        Calendar calendar = (Calendar) selectedDate.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for( int i = 1; i <= maxDay; i++ ) {
            Calendar day = (Calendar) calendar.clone();
            day.set(Calendar.DAY_OF_MONTH, i);
            dayList.add(day);
        }

        return dayList;
    }

    private void refreshTaskListRecyclerView() {
        // - Clear the existing dayTaskMap
        dayTaskMap.clear();

        // - Generate the list of days for the selected month
        List<Calendar> dayList = generateDaysForMonth(selectedDate);

        // - Get all tasks for the selected month
        int selectedYear = selectedDate.get(Calendar.YEAR);
        int selectedMonth = selectedDate.get(Calendar.MONTH);

        DatabaseHandler dbHandler = new DatabaseHandler(getContext());
        List<Task> tasksForMonth = dbHandler.getAllTasksByMonth(selectedYear, selectedMonth);

        // - Populate the newDayTaskMap with tasks for each day
        for( Task task : tasksForMonth ) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
            String dueDateString = task.getDueDate();
            Calendar taskDueDateCalendar = Calendar.getInstance();
            try {
                Date dueDate = dateFormat.parse(dueDateString);
                taskDueDateCalendar.setTime(dueDate);
                int dayOfMonth = taskDueDateCalendar.get(Calendar.DAY_OF_MONTH);
                if( !dayTaskMap.containsKey(dayOfMonth)) {
                    dayTaskMap.put(dayOfMonth, new ArrayList<>());
                }
                dayTaskMap.get(dayOfMonth).add(task);
            } catch( ParseException e ) {
                e.printStackTrace();
            }
        }

        // - Update the dayTaskMap and set the new adapter
        dayAdapter = new TimelineDayAdapter(dayList, dayTaskMap, this);
        rvTaskList.setAdapter(dayAdapter);
    }
}