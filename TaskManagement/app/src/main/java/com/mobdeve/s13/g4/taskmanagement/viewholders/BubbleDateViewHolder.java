package com.mobdeve.s13.g4.taskmanagement.viewholders;

import com.mobdeve.s13.g4.taskmanagement.R;

import android.view.View;
import android.widget.TextView;import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BubbleDateViewHolder extends RecyclerView.ViewHolder {

    TextView tvDate;

    public BubbleDateViewHolder(@NonNull View itemView) {
        super(itemView);
        tvDate = itemView.findViewById(R.id.tvDate);
    }

    public TextView getDate() {
        return tvDate;
    }

}