package com.mobdeve.s13.g4.taskmanagement.adapters;


import com.mobdeve.s13.g4.taskmanagement.R;

import androidx.core.content.ContextCompat;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AssignDateAdapter extends BaseAdapter {

    private Context context;
    private Calendar currentDate;
    private Calendar selectedDate;
    private TextView tvMonthYear;

    private int daysInMonth;
    private int firstDayOfWeek;

    public AssignDateAdapter( Context context, Calendar currentDate, TextView tvMonthYear ) {
        this.context = context;
        this.currentDate = currentDate;
        this.selectedDate = (Calendar)currentDate.clone();
        this.tvMonthYear = tvMonthYear;
        calculateCalendarDetails();
    }

    private void calculateCalendarDetails() {
        daysInMonth = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH);

        Calendar firstDayOfMonth = (Calendar) currentDate.clone();
        firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        firstDayOfWeek = firstDayOfMonth.get(Calendar.DAY_OF_WEEK) - 2; // Adjust for zero-based index and starting from Monday
        if( firstDayOfWeek < 0 ) {
            firstDayOfWeek += 7; // Handle cases where the first day is Sunday (0)
        }
    }

    @Override
    public int getCount() {
        return daysInMonth + firstDayOfWeek + (7 - ((daysInMonth + firstDayOfWeek) % 7)) % 7; // Add extra cells for last week
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_calendar_numbers_date, parent, false);
        }

        TextView tvDay = convertView.findViewById(R.id.tvDay);
        int dayOfMonth = position - firstDayOfWeek + 1;
        tvDay.setBackgroundResource(0);

        if( position < firstDayOfWeek ) {
            // Show days from the previous month
            Calendar prevMonth = (Calendar) currentDate.clone();
            prevMonth.add(Calendar.MONTH, -1);
            int prevMonthDays = prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
            tvDay.setText(String.valueOf(prevMonthDays - firstDayOfWeek + position + 1));
            tvDay.setTextColor(ContextCompat.getColor(context, R.color.gray));

            tvDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentDate.add(Calendar.MONTH, -1);
                    selectedDate = (Calendar) currentDate.clone();
                    selectedDate.set(Calendar.DAY_OF_MONTH, prevMonthDays - firstDayOfWeek + position + 1);
                    updateMonthYearText();
                    calculateCalendarDetails();
                    notifyDataSetChanged();
                }
            });
        } else if( dayOfMonth > daysInMonth ) {
            // Show days from the next month
            tvDay.setText(String.valueOf(dayOfMonth - daysInMonth));
            tvDay.setTextColor(ContextCompat.getColor(context, R.color.gray));

            tvDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentDate.add(Calendar.MONTH, 1);
                    selectedDate = (Calendar) currentDate.clone();
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth - daysInMonth);
                    updateMonthYearText();
                    calculateCalendarDetails();
                    notifyDataSetChanged();
                }
            });
        } else {
            // Show days from the current month
            tvDay.setText(String.valueOf(dayOfMonth));
            tvDay.setTextColor(ContextCompat.getColor(context, R.color.black));

            Calendar calendar = (Calendar) currentDate.clone();
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            if( calendar.equals(selectedDate) ) {
                tvDay.setBackgroundResource(R.drawable.selected_date_background);
            } else {
                tvDay.setBackgroundResource(0);
            }

            tvDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedDate = (Calendar) calendar.clone();
                    updateMonthYearText();
                    notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }


    public Calendar getSelectedDate() {
        return selectedDate;
    }

    private void updateMonthYearText() {
        tvMonthYear.setText(new SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(currentDate.getTime()));
    }
}