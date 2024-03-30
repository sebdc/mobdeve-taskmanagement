package com.mobdeve.s13.g4.taskmanagement.adapters;

import com.mobdeve.s13.g4.taskmanagement.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class PriorityAdapter extends RecyclerView.Adapter<PriorityAdapter.PriorityViewHolder> {

    private List<String> priorityList;
    private int selectedPosition = -1;
    private OnPriorityClickListener listener;

    public interface OnPriorityClickListener {
        void onPriorityClick(String priority);
    }

    public PriorityAdapter(List<String> priorityList, OnPriorityClickListener listener) {
        this.priorityList = priorityList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PriorityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_priority, parent, false);
        return new PriorityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PriorityViewHolder holder, int position) {
        String priority = priorityList.get(position);
        holder.bind(priority);
    }

    @Override
    public int getItemCount() {
        return priorityList.size();
    }

    public class PriorityViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPriorityName;
        private LinearLayout priorityItemLayout;

        public PriorityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPriorityName = itemView.findViewById(R.id.tvPriorityName);
            priorityItemLayout = itemView.findViewById(R.id.priorityItemLayout);
        }

        public void bind(String priority) {
            tvPriorityName.setText(priority);

            if (getAdapterPosition() == selectedPosition) {
                priorityItemLayout.setBackgroundResource(R.drawable.selected_priority_background);
            } else {
                priorityItemLayout.setBackgroundResource(0);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onPriorityClick(priority);
                    }
                    selectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}