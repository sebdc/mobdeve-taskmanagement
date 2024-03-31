package com.mobdeve.s13.g4.taskmanagement.fragments;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.adapters.*;
import com.mobdeve.s13.g4.taskmanagement.database.DatabaseHandler;
import com.mobdeve.s13.g4.taskmanagement.models.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AssignCategoryDialog extends BottomSheetDialogFragment {

    private AssignCategoryAdapter assignCategoryAdapter;
    private List<Category> categoryList;
    private OnCategorySelectedListener listener;

    // - XML Attributes
    private View view;
    private RecyclerView rvCategoryList;
    private Button btnCreateCategory;
    private Button btnDone;

    /*|*******************************************************
                        Constructor Methods
    *********************************************************/
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        view = inflater.inflate(R.layout.dialog_assign_category, container, false);
        List<Category> tempCategoryList;

        // - Database setup
        DatabaseHandler dbHandler = new DatabaseHandler(getContext());
        tempCategoryList = dbHandler.getAllCategories();
        sortCategoriesByDateCreated(tempCategoryList);

        // - Create a pseudo "none" category
        Category noneCategory = new Category("None");
        categoryList = new ArrayList<>();
        categoryList.add(noneCategory);
        categoryList.addAll(tempCategoryList);

        // - Dialog view and functionality setup
        findAllViews();
        setupAdapterFunctionality();
        setupButtonsFunctionality();

        // - Recycler view setup
        rvCategoryList.setLayoutManager( new LinearLayoutManager(getContext()) );
        rvCategoryList.setAdapter(assignCategoryAdapter);

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

    /*|*******************************************************
                       Initialize Methods
    *********************************************************/
    private void findAllViews() {
        rvCategoryList = view.findViewById(R.id.rvCategoryList);
        btnCreateCategory = view.findViewById(R.id.btnCreateCategory);
        btnDone = view.findViewById(R.id.btnDone);
    }

    private void setupButtonsFunctionality() {
        btnCreateCategory.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateCategoryDialog();
            }
        });

        btnDone.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void setupAdapterFunctionality() {
        assignCategoryAdapter = new AssignCategoryAdapter( categoryList, new AssignCategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick( Category category ) {
                if( listener != null ) {
                    listener.onCategorySelected(category);
                    dismiss();
                }
            }
        });
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
                    layoutParams.height = getScreenHeight() / 2;
                    bottomSheet.setLayoutParams(layoutParams);
                }
            }
        });

        return dialog;
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

    private int getScreenHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /*|*******************************************************
                        Dialogue Methods
    *********************************************************/
    private void sortCategoriesByDateCreated( List<Category> categoryList ) {
        Collections.sort( categoryList, new Comparator<Category>() {
            @Override
            public int compare(Category c1, Category c2) {
                return c1.getDateCreated().compareTo(c2.getDateCreated());
            }
        });
    }
}