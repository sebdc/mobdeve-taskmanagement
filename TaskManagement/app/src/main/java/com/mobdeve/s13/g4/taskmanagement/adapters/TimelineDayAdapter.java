package com.mobdeve.s13.g4.taskmanagement.adapters;

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


public class TimelineDayAdapter extends RecyclerView.Adapter<TimelineDayAdapter.DayViewHolder> {

    // - Attributes
    private List<Calendar> dayList;
    Map<Integer, List<Task>> dayTaskMap;
    private ActivityResultLauncher<Intent> updateTaskLauncher;

    public TimelineDayAdapter( List<Calendar> dayList, Map<Integer, List<Task>> dayTaskMap, ActivityResultLauncher<Intent> updateTaskLauncher ) {
        this.dayList = dayList;
        this.dayTaskMap = dayTaskMap;
        this.updateTaskLauncher = updateTaskLauncher;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeline_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder( @NonNull DayViewHolder holder, int position ) {
        Calendar day = dayList.get(position);
        holder.bind(day);
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    /*|*******************************************************
                        DayViewHolder Class
    *********************************************************/
    public class DayViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDateTitle;
        private RecyclerView rvTasksForDate;

        public DayViewHolder( @NonNull View itemView ) {
            super(itemView);
            tvDateTitle = itemView.findViewById(R.id.tvDateTitle);
            rvTasksForDate = itemView.findViewById(R.id.rvTasksForDate);
        }

        public void bind( Calendar day ) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
            String dateTitle = dateFormat.format(day.getTime());
            tvDateTitle.setText(dateTitle);

            // - Set up the inner RecyclerView for tasks
            int dayOfMonth = day.get(Calendar.DAY_OF_MONTH);
            List<Task> tasksForDay = dayTaskMap.get(dayOfMonth);

            if( tasksForDay == null ) {
                tasksForDay = new ArrayList<>();
                Log.d("tasksForDayCheck()", "tasksForDay is empty on " + dayOfMonth);
            } else {
                Log.d("tasksForDayCheck()", "tasksForDay has tasks on " + dayOfMonth);
            }

            TimelineTaskAdapter taskAdapter = new TimelineTaskAdapter(tasksForDay, updateTaskLauncher);
            rvTasksForDate.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            rvTasksForDate.setAdapter(taskAdapter);
        }
    }
}