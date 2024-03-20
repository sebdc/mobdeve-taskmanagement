package com.mobdeve.s13.g4.taskmanagement.activities;

import com.mobdeve.s13.g4.taskmanagement.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class BottomNavigationView extends LinearLayout {

    // - Class Attributes
    private ImageButton btnViewSidebar;
    private ImageButton btnViewHome;
    private ImageButton btnAddTask;
    private ImageButton btnViewCalendar;
    private ImageButton btnViewProfile;

    /*|*******************************************************
                        Constructor Methods
    *********************************************************/
    public BottomNavigationView(Context context) {
        super(context);
        initView(context);
    }

    public BottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /*|*******************************************************
                        Behaviour Methods
    *********************************************************/
    private void initView( Context context ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bottom_navigation, this, true);

        btnViewSidebar = view.findViewById(R.id.btnViewSidebar);
        btnViewHome = view.findViewById(R.id.btnViewHome);
        btnAddTask = view.findViewById(R.id.btnAddTask);
        btnViewCalendar = view.findViewById(R.id.btnViewCalendar);
        btnViewProfile = view.findViewById(R.id.btnViewProfile);
    }

    /*|*******************************************************
                        Getters & Setters
        Do not modify these methods as they are designed to
        only serve the purpose of accessing and updating the
        state of User objects.
    *********************************************************/
    public ImageButton getBtnViewSidebar()  { return btnViewSidebar; }
    public ImageButton getBtnViewHome()     { return btnViewHome; }
    public ImageButton getBtnAddTask()      { return btnAddTask; }
    public ImageButton getBtnViewCalendar() { return btnViewCalendar; }
    public ImageButton getBtnViewProfile()  { return btnViewProfile; }
}