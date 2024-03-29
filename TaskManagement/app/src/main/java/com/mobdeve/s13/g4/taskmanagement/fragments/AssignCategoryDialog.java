package com.mobdeve.s13.g4.taskmanagement.fragments;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.adapters.*;
import com.mobdeve.s13.g4.taskmanagement.models.*;
import com.mobdeve.s13.g4.taskmanagement.database.*;
import com.mobdeve.s13.g4.taskmanagement.viewholders.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class AssignCategoryDialog extends BottomSheetDialogFragment {

    // - XML Attributes
    private RecyclerView rvCategoryList;
    private AssignCategoryAdapter assignCategoryAdapter;
    private List<Category> categoryList;
    private OnCategorySelectedListener listener;

    /*|*******************************************************
                        Constructor Methods
    *********************************************************/
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.dialog_assign_category, container, false);

        rvCategoryList = view.findViewById(R.id.rvCategoryList);
        Button btnCreateCategory = view.findViewById(R.id.btnCreateCategory);
        Button btnDone = view.findViewById(R.id.btnDone);

        categoryList = new ArrayList<>();
        assignCategoryAdapter = new AssignCategoryAdapter( categoryList, new AssignCategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(Category category) {
                if( listener != null ) {
                    listener.onCategorySelected(category);
                    dismiss();
                }
            }
        });

        rvCategoryList.setLayoutManager( new LinearLayoutManager(getContext()) );
        rvCategoryList.setAdapter(assignCategoryAdapter);

        btnCreateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateCategoryDialog();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }


    public interface OnCategorySelectedListener {
        void onCategorySelected( Category category );
    }

    public static AssignCategoryDialog newInstance() {
        return new AssignCategoryDialog();
    }

    public void setOnCategorySelectedListener( OnCategorySelectedListener listener ) {
        this.listener = listener;
    }

    private void openCreateCategoryDialog() {
        CreateCategoryDialog dialog = CreateCategoryDialog.newInstance();
        dialog.setOnCategoryCreatedListener(new CreateCategoryDialog.OnCategoryCreatedListener() {
            @Override
            public void onCategoryCreated(Category category) {
                categoryList.add(category);
                assignCategoryAdapter.notifyDataSetChanged();
            }
        });
        dialog.show(getParentFragmentManager(), "CreateCategoryDialog");
    }

    /*|*******************************************************
                        Dialogue Methods
    *********************************************************/
    @NonNull
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        dialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow( DialogInterface dialogInterface ) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                FrameLayout bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);

                if( bottomSheet != null ) {
                    BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
                    behavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                    behavior.setSkipCollapsed(true);
                    behavior.setHideable(false);

                    // - Set the height of the bottom sheet to half of the screen height
                    ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
                    layoutParams.height = getScreenHeight() / 3;
                    bottomSheet.setLayoutParams(layoutParams);
                }
            }
        });

        return dialog;
    }

    private int getScreenHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
}