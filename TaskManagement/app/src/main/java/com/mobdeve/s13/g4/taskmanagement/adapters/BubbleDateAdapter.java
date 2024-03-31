package com.mobdeve.s13.g4.taskmanagement.adapters;

import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.viewholders.*;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class BubbleDateAdapter extends RecyclerView.Adapter<BubbleDateViewHolder> {
    private List<Calendar> dates;
    private Calendar selectedDate;
    private OnDateSelectedListener onDateSelectedListener;

    public interface OnDateSelectedListener {
        void onDateSelected( Calendar date, int dayNumber );
    }

    public BubbleDateAdapter( List<Calendar> dates, OnDateSelectedListener onDateSelectedListener ) {
        this.dates = dates;
        this.onDateSelectedListener = onDateSelectedListener;
    }

    @NonNull
    @Override
    public BubbleDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bubble_date, parent, false);
        return new BubbleDateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BubbleDateViewHolder holder, int position) {
        Calendar date = dates.get(position);
        TextView tvDayNumber = holder.getTvDayNumber();
        TextView tvWeekDay = holder.getTvWeekday();

        // - Set day of the month
        int dayNumber = date.get(Calendar.DAY_OF_MONTH);
        tvDayNumber.setText( String.valueOf(dayNumber) );

        // - Set day of the week
        SimpleDateFormat weekdayFormat = new SimpleDateFormat("EEE", Locale.getDefault());
        String weekday = weekdayFormat.format(date.getTime());
        tvWeekDay.setText(weekday);

        boolean isSelected;
        if( date != null && selectedDate != null ) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = dateFormat.format(date.getTime());
            String formattedSelectedDate = dateFormat.format(selectedDate.getTime());
            isSelected = formattedDate.equals(formattedSelectedDate);
        } else {
            isSelected = date.equals(selectedDate);
        }

        holder.itemView.setSelected(isSelected);

        int dayNumberColor = isSelected ? Color.WHITE : Color.BLACK;
        int weekdayColor = isSelected ? Color.WHITE : Color.GRAY;

        tvDayNumber.setTextColor(dayNumberColor);
        tvWeekDay.setTextColor(weekdayColor);

        // - Set the background color using a ColorStateList
        int backgroundResId = isSelected ? R.drawable.bg_bubble_date_selected : R.drawable.bg_bubble_date;
        holder.itemView.setBackgroundResource(backgroundResId);

        holder.itemView.setOnClickListener(v -> {
            selectedDate = date;
            notifyDataSetChanged();
            onDateSelectedListener.onDateSelected(date, dayNumber);
        });
    }

    public void setDates(List<Calendar> newDates) {
        dates.clear();
        dates.addAll(newDates);
        notifyDataSetChanged();
    }

    public void setSelectedDate( Calendar selectedDate ) {
        this.selectedDate = selectedDate;
        String formattedDate = String.format("setSelectedDate - Date Selected: %1$tD", selectedDate);
        Log.d("setSelectedDate()", formattedDate);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }
}