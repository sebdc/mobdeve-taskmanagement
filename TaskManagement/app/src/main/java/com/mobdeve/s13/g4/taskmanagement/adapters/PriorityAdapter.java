package com.mobdeve.s13.g4.taskmanagement.adapters;

import com.mobdeve.s13.g4.taskmanagement.R;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;
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
        private ConstraintLayout priorityItemLayout;
        private ImageView ivFlagIcon;

        public PriorityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPriorityName = itemView.findViewById(R.id.tvPriorityName);
            priorityItemLayout = itemView.findViewById(R.id.priorityItemLayout);
            ivFlagIcon = itemView.findViewById(R.id.ivFlagIcon);
        }

        public void bind( String priority ) {
            tvPriorityName.setText(priority);
            handleFlagIconColor(priority);

            if( getAdapterPosition() == selectedPosition ) {
                priorityItemLayout.setBackgroundResource(R.drawable.selected_priority_background);
            } else {
                priorityItemLayout.setBackgroundResource(0);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( listener != null ) {
                        listener.onPriorityClick(priority);
                    }
                    selectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }

        private void handleFlagIconColor(String priorityLevel) {
            switch (priorityLevel) {
                case "High": {
                    ivFlagIcon.setVisibility(View.VISIBLE);
                    ivFlagIcon.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.high), PorterDuff.Mode.SRC_IN);
                    tvPriorityName.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.high));
                }
                break;
                case "Medium": {
                    ivFlagIcon.setVisibility(View.VISIBLE);
                    ivFlagIcon.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.medium), PorterDuff.Mode.SRC_IN);
                    tvPriorityName.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.medium));
                }
                break;
                case "Low": {
                    ivFlagIcon.setVisibility(View.VISIBLE);
                    ivFlagIcon.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.low), PorterDuff.Mode.SRC_IN);
                    tvPriorityName.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.low));
                }
                break;
                default: {
                    ivFlagIcon.setVisibility(View.VISIBLE);
                    ivFlagIcon.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.gray), PorterDuff.Mode.SRC_IN);
                    tvPriorityName.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.gray));
                }
            }
        }
    }
}