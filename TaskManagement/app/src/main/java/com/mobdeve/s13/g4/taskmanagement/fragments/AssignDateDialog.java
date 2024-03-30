package com.mobdeve.s13.g4.taskmanagement.fragments;

import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.adapters.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;

import android.view.LayoutInflater;
import android.view.View;

import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AssignDateDialog extends DialogFragment {

    private TextView tvMonthYear;
    private ImageButton btnPrevMonth;
    private ImageButton btnNextMonth;

    private GridView gvWeekdays;
    private GridView gvCalendar;

    private Button btnCancel;
    private Button btnSelect;

    private AssignDateAdapter calendarAdapter;
    private Calendar currentDate;

    private OnDateSelectedListener listener;

    public interface OnDateSelectedListener {
        void onDateSelected(Calendar selectedDate);
    }

    public static AssignDateDialog newInstance() {
        return new AssignDateDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog( @Nullable Bundle savedInstanceState ) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_assign_date, null);

        tvMonthYear = view.findViewById(R.id.tvMonthYear);
        btnPrevMonth = view.findViewById(R.id.btnPrevMonth);
        btnNextMonth = view.findViewById(R.id.btnNextMonth);
        gvWeekdays = view.findViewById(R.id.gvWeekdays);
        gvCalendar = view.findViewById(R.id.gvCalendar);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnSelect = view.findViewById(R.id.btnSelect);

        currentDate = Calendar.getInstance();
        updateCalendar();

        btnPrevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

        btnNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDateSelected(calendarAdapter.getSelectedDate());
                }
                dismiss();
            }
        });

        String[] weekdays = new String[]{"M", "T", "W", "T", "F", "S", "S"};
        ArrayAdapter<String> weekdayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, weekdays);
        gvWeekdays.setAdapter(weekdayAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();
    }

    private void updateCalendar() {
        tvMonthYear.setText(new SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(currentDate.getTime()));
        calendarAdapter = new AssignDateAdapter(getActivity(), currentDate, tvMonthYear);
        gvCalendar.setAdapter(calendarAdapter);
    }

    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        this.listener = listener;
    }
}