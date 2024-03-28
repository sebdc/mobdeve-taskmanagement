package com.mobdeve.s13.g4.taskmanagement.activities;

import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.database.DatabaseHandler;
import com.mobdeve.s13.g4.taskmanagement.fragments.*;
import com.mobdeve.s13.g4.taskmanagement.utility.*;
import com.mobdeve.s13.g4.taskmanagement.models.*;


import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    private EditText etTaskTitle;
    private EditText etTaskDescription;
    private Button btnAssignCategory;
    private Button btnViewCreateCategory;
    private ImageButton btnCreateCategory;
    private RecyclerView rvCategoryList;
    // private CategoryAdapter categoryAdapter;
    private List<Category> categoryList;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etTaskTitle = findViewById(R.id.etTaskTitle);
        etTaskDescription = findViewById(R.id.etTaskDescription);
        btnAssignCategory = findViewById(R.id.btnAssignCategory);
        btnViewCreateCategory = findViewById(R.id.btnViewCreateCategory);
        btnCreateCategory = findViewById(R.id.btnCreateCategory);
        rvCategoryList = findViewById(R.id.rvCategoryList);

    }


}