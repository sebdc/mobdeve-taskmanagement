package com.mobdeve.s13.g4.taskmanagement.fragments;


import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.adapters.*;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class AssignPriorityDialog extends BottomSheetDialogFragment {

    private RecyclerView rvPriority;
    private PriorityAdapter priorityAdapter;
    private List<String> priorityList;
    private OnPrioritySelectedListener listener;

    public interface OnPrioritySelectedListener {
        void onPrioritySelected(String priority);
    }

    public static AssignPriorityDialog newInstance() {
        return new AssignPriorityDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_assign_priority, container, false);

        rvPriority = view.findViewById(R.id.rvPriority);
        Button btnDone = view.findViewById(R.id.btnDone);

        priorityList = new ArrayList<>();
        priorityList.add("High");
        priorityList.add("Medium");
        priorityList.add("Low");
        priorityList.add("None");

        priorityAdapter = new PriorityAdapter(priorityList, new PriorityAdapter.OnPriorityClickListener() {
            @Override
            public void onPriorityClick(String priority) {
                if( listener != null ) {
                    listener.onPrioritySelected(priority);
                }
            }
        });
        rvPriority.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPriority.setAdapter(priorityAdapter);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    public void setOnPrioritySelectedListener(OnPrioritySelectedListener listener) {
        this.listener = listener;
    }
}