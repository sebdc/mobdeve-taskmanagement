package com.mobdeve.s13.g4.taskmanagement.activities;

import com.mobdeve.s13.g4.taskmanagement.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BottomNavigationView extends LinearLayout {

    // - Class Attributes
    private ImageButton btnViewTaskList;
    private ImageButton btnViewHome;
    private ImageButton btnViewTimeline;
    private TextView txtViewTaskList;
    private TextView txtViewHome;
    private TextView txtViewTimeline;

    /*|*******************************************************
                        Constructor Methods
    *********************************************************/
    public BottomNavigationView( Context context ) {
        super(context);
        initView(context);
    }

    public BottomNavigationView( Context context, AttributeSet attrs ) {
        super(context, attrs);
        initView(context);
    }

    public BottomNavigationView( Context context, AttributeSet attrs, int defStyleAttr ) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /*|*******************************************************
                        Behaviour Methods
    *********************************************************/
    private void initView( Context context ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bottom_navigation, this, true);

        btnViewTaskList = view.findViewById(R.id.btnViewTaskList);
        btnViewHome = view.findViewById(R.id.btnViewHome);
        btnViewTimeline = view.findViewById(R.id.btnViewTimeline);
        txtViewTaskList = view.findViewById(R.id.txtViewTaskList);
        txtViewHome = view.findViewById(R.id.txtViewHome);
        txtViewTimeline = view.findViewById(R.id.txtViewTimeline);
    }

    /*|*******************************************************
                        Getters & Setters
        Do not modify these methods as they are designed to
        only serve the purpose of accessing and updating the
        state of BottomNavigationView objects.
    *********************************************************/
    public ImageButton getBtnViewTaskList()     { return btnViewTaskList; }
    public ImageButton getBtnViewHome()         { return btnViewHome; }
    public ImageButton getBtnViewTimeline()     { return btnViewTimeline; }

    public TextView getTxtViewTaskList()     { return txtViewTaskList; }
    public TextView getTxtViewHome()         { return txtViewHome; }
    public TextView getTxtViewTimeline()     { return txtViewTimeline; }

}