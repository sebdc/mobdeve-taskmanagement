package com.mobdeve.s13.g4.taskmanagement.viewholders;

import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.adapters.CreateCategoryAdapter;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;

public class CreateCategoryViewHolder extends RecyclerView.ViewHolder {

    private View colorView;

    public CreateCategoryViewHolder( @NonNull View itemView ) {
        super(itemView);
        // colorView = itemView.findViewById(R.id.colorView);
    }

    public void bind( int color, CreateCategoryAdapter.OnColorClickListener listener ) {
        colorView.setBackgroundColor(color);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( listener != null ) {
                    listener.onColorClick(color);
                }
            }
        });
    }
}