package com.mobdeve.s13.g4.taskmanagement.viewholders;

import com.mobdeve.s13.g4.taskmanagement.R;

import android.view.View;
import android.widget.TextView;import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BubbleDateViewHolder extends RecyclerView.ViewHolder {

    private TextView tvDayNumber;
    private TextView tvWeekday;

    public BubbleDateViewHolder(@NonNull View itemView) {
        super(itemView);
        tvDayNumber = itemView.findViewById(R.id.tvDayNumber);
        tvWeekday = itemView.findViewById(R.id.tvWeekday);
    }

    public TextView getTvDayNumber() {
        return tvDayNumber;
    }

    public TextView getTvWeekday() {
        return tvWeekday;
    }

}