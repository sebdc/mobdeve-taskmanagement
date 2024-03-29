package com.mobdeve.s13.g4.taskmanagement.adapters;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.adapters.*;
import com.mobdeve.s13.g4.taskmanagement.models.*;
import com.mobdeve.s13.g4.taskmanagement.database.*;
import com.mobdeve.s13.g4.taskmanagement.viewholders.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

public class AssignDateAdapter extends BaseAdapter {

    private Context context;
    private Calendar currentDate;
    private Calendar selectedDate;

    public AssignDateAdapter(Context context, Calendar currentDate) {
        this.context = context;
        this.currentDate = currentDate;
        this.selectedDate = (Calendar)currentDate.clone();
    }

    @Override
    public int getCount() {
        return currentDate.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if( convertView == null ) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_calendar_day, parent, false);
        }

        TextView tvDay = convertView.findViewById(R.id.tvDay);

        Calendar calendar = (Calendar) currentDate.clone();
        calendar.set(Calendar.DAY_OF_MONTH, position + 1);

        tvDay.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

        if( calendar.equals(selectedDate) ) {
            tvDay.setBackgroundResource(R.drawable.selected_date_background);
        } else {
            tvDay.setBackgroundResource(0);
        }

        tvDay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                selectedDate = (Calendar) calendar.clone();
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public Calendar getSelectedDate() {
        return selectedDate;
    }
}