package com.mobdeve.s13.g4.taskmanagement.utility;

import android.annotation.SuppressLint;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.activities.*;
import com.mobdeve.s13.g4.taskmanagement.fragments.*;

import androidx.core.content.ContextCompat;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class NavigationHandler {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private ImageButton currentSelectedButton;
    private TextView currentSelectedText;

    public NavigationHandler(BottomNavigationView bottomNavigationView, FragmentManager fragmentManager) {
        this.bottomNavigationView = bottomNavigationView;
        this.fragmentManager = fragmentManager;
        setupNavigationClickListeners();
    }

    private void setupNavigationClickListeners() {
        bottomNavigationView.getBtnViewTaskList().setOnClickListener(v -> handleButtonClick( bottomNavigationView.getBtnViewTaskList(), new TaskListFragment() ));
        bottomNavigationView.getBtnViewHome().setOnClickListener(v -> handleButtonClick( bottomNavigationView.getBtnViewHome(), new HomeFragment() ));
        bottomNavigationView.getBtnViewTimeline().setOnClickListener(v -> handleButtonClick( bottomNavigationView.getBtnViewTimeline(), new TimelineFragment() ));

        // - Set the initial selected button and text
        currentSelectedButton = bottomNavigationView.getBtnViewHome();
        currentSelectedText = bottomNavigationView.getTxtViewHome();
        setSelectedButtonAndText(currentSelectedButton, currentSelectedText);

    }

    private void handleButtonClick( ImageButton clickedButton, Fragment fragment ) {
        if( currentSelectedButton != clickedButton ) {
            // - Reset the previously selected button and text to default state
            setDefaultButtonAndText(currentSelectedButton, currentSelectedText);

            // - Update the current selected button and text
            currentSelectedButton = clickedButton;
            currentSelectedText = getTextViewForButton(clickedButton);
            setSelectedButtonAndText( currentSelectedButton, currentSelectedText );

            // - Navigate to the corresponding fragment
            showFragment(fragment);
        }
    }

    private void setDefaultButtonAndText( ImageButton button, TextView textView ) {
        if( button == bottomNavigationView.getBtnViewTaskList() ) {
            button.setImageResource(R.drawable.task_line);
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.gray));
        } else if( button == bottomNavigationView.getBtnViewHome() ) {
            button.setImageResource(R.drawable.home_line);
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.gray));
        } else if( button == bottomNavigationView.getBtnViewTimeline() ) {
            button.setImageResource(R.drawable.timeline_line);
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.gray));
        }
    }

    private void setSelectedButtonAndText( ImageButton button, TextView textView ) {
        if( button == bottomNavigationView.getBtnViewTaskList() ) {
            button.setImageResource(R.drawable.task_fill);
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.blue));
        } else if( button == bottomNavigationView.getBtnViewHome() ) {
            button.setImageResource(R.drawable.home_fill);
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.blue));
        } else if( button == bottomNavigationView.getBtnViewTimeline() ) {
            button.setImageResource(R.drawable.timeline_fill);
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.blue));
        }
    }

    private TextView getTextViewForButton( ImageButton button ) {
        if( button == bottomNavigationView.getBtnViewTaskList() ) {
            return bottomNavigationView.getTxtViewTaskList();
        } else if( button == bottomNavigationView.getBtnViewHome() ) {
            return bottomNavigationView.getTxtViewHome();
        } else if( button == bottomNavigationView.getBtnViewTimeline() ) {
            return bottomNavigationView.getTxtViewTimeline();
        }
        return null;
    }

    public void showFragment( Fragment fragment ) {
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }
}