package com.mobdeve.s13.g4.taskmanagement.fragments;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.database.DatabaseHandler;
import com.mobdeve.s13.g4.taskmanagement.models.*;


import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;

public class CreateCategoryDialog extends BottomSheetDialogFragment {

    private OnCategoryCreatedListener listener;

    // - XML Attributes
    private View view;
    private EditText etCategoryName;
    private GridLayout colorSelectionLayout;
    private ImageButton btnCreateCategory;

    /*|*******************************************************
                       Constructor Methods
    *********************************************************/
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        view = inflater.inflate(R.layout.dialog_create_category, container, false);

        findAllViews();
        setupButtonsFunctionality();
        /*
                Set up color selection grid here
         */

        return view;
    }

    public interface OnCategoryCreatedListener {
        void onCategoryCreated(Category category);
    }

    public static CreateCategoryDialog newInstance() {
        return new CreateCategoryDialog();
    }

    public void setOnCategoryCreatedListener(OnCategoryCreatedListener listener) {
        this.listener = listener;
    }

    /*|*******************************************************
                       Initialize Methods
    *********************************************************/
    private void findAllViews() {
        etCategoryName = view.findViewById(R.id.etCategoryName);
        colorSelectionLayout = view.findViewById(R.id.colorSelectionLayout);
        btnCreateCategory = view.findViewById(R.id.btnCreateCategory);
    }

    private void setupButtonsFunctionality() {
        btnCreateCategory.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                String categoryName = etCategoryName.getText().toString().trim();
                if( !categoryName.isEmpty() ) {
                    Category category = new Category(categoryName);
                    // - Set additional properties if needed (e.g., mainColor, subColor)
                    // - category.setMainColor(...);
                    // - category.setSubColor(...);

                    // - Save the category to the database
                    DatabaseHandler dbHandler = new DatabaseHandler(getContext());
                    dbHandler.insertCategory(category);

                    if( listener != null ) {
                        listener.onCategoryCreated(category);
                    }
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
    public Dialog onCreateDialog(Bundle savedInstanceState ) {
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