package com.mobdeve.s13.g4.taskmanagement.adapters;

import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.viewholders.*;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public BubbleDateAdapter(List<Calendar> dates, OnDateSelectedListener onDateSelectedListener) {
        this.dates = dates;
        this.onDateSelectedListener = onDateSelectedListener;
        this.selectedDate = dates.get(0);
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
        SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.getDefault());
        SimpleDateFormat weekdayFormat = new SimpleDateFormat("EEE", Locale.getDefault());

        String dayText = dayFormat.format(date.getTime());
        String weekdayText = weekdayFormat.format(date.getTime());

        holder.getDate().setText(String.format("%s\n%s", dayText, weekdayText));

        holder.itemView.setOnClickListener(v -> {
            selectedDate = date;
            onDateSelectedListener.onDateSelected(date);
            notifyDataSetChanged();
        });

        holder.itemView.setSelected(date.equals(selectedDate));
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public interface OnDateSelectedListener {
        void onDateSelected(Calendar date);
    }
}