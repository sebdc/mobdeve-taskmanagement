package com.mobdeve.s13.g4.taskmanagement.fragments;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.mobdeve.s13.g4.taskmanagement.R;
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

public class TimelineFragment extends Fragment {

    // - Attributes
    private Calendar selectedDate;
    private List<Task> taskList;
    private Map<Integer, List<Task>> dayTaskMap;
    private ActivityResultLauncher<Intent> updateTaskLauncher;

    // - Header
    private TextView tvHeaderTitle;
    private ImageButton btnCalendar;

    // - Tasks
    private RecyclerView rvTaskList;
    private TimelineDayAdapter dayAdapter;

    // - Bubble Dates
    private RecyclerView rvBubbleDates;
    private BubbleDateAdapter bubbleDateAdapter;

    /*|*******************************************************
                        Constructor Methods
    *********************************************************/
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

        setupTaskLauncher();

        // - Inflate the layout for this fragment
        return view;
    }

    /*|*******************************************************
                      Initialization Methods
    *********************************************************/
    private void findAllViews( View view ) {
        tvHeaderTitle = view.findViewById(R.id.tvHeaderTitle);
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

        dayAdapter = new TimelineDayAdapter(dayList, dayTaskMap, updateTaskLauncher);
        rvTaskList.setAdapter(dayAdapter);
    }

    private void setupTaskLauncher() {
        updateTaskLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if( result.getResultCode() == Activity.RESULT_OK ) {
                        // Handle the result if needed
                        // For example, refresh the task list
                        refreshTaskList();
                        dayAdapter.notifyDataSetChanged();
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

        // Log the contents of dayTaskMap
        Log.d("dayTaskMapContents", "Logging dayTaskMap contents:");
        for( Map.Entry<Integer, List<Task>> entry : dayTaskMap.entrySet() ) {
            int dayOfMonth = entry.getKey();
            List<Task> tasksForDay = entry.getValue();
            Log.d("dayTaskMapContents", "Day of month: " + dayOfMonth);
            Log.d("dayTaskMapContents", "Tasks for day " + dayOfMonth + ":");
            for (Task task : tasksForDay) {
                Log.d("dayTaskMapContents", "Task: " + task.getTitle());
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
    }


    private void updateBubbleDateRecyclerView( Calendar calendar ) {
        List<Calendar> dates = DateDataHelper.generateDatesForMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
        bubbleDateAdapter.setDates(dates);
        bubbleDateAdapter.setSelectedDate(selectedDate);
    }


    /*|*******************************************************
                         Task List
    *********************************************************/
    public void refreshTaskList() {
        DatabaseHandler dbHandler = new DatabaseHandler(getContext());
        taskList.clear();
        taskList.addAll(dbHandler.getAllTasks());
        dayAdapter.notifyDataSetChanged();
    }

    private List<Calendar> generateDaysForMonth( Calendar selectedDate ) {
        List<Calendar> dayList = new ArrayList<>();

        Calendar calendar = (Calendar) selectedDate.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= maxDay; i++) {
            Calendar day = (Calendar) calendar.clone();
            day.set(Calendar.DAY_OF_MONTH, i);
            dayList.add(day);
        }

        return dayList;
    }
}