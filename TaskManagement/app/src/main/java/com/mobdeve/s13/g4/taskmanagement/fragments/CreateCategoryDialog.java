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
import android.widget.ImageView;

import android.content.res.ColorStateList;
import androidx.core.widget.ImageViewCompat;
import androidx.core.content.ContextCompat;
import android.graphics.Color;

public class CreateCategoryDialog extends BottomSheetDialogFragment {

    private String selectedColor = "";
    private ImageView selectedColorView;
    private OnCategoryCreatedListener listener;

    // - XML Attributes
    private View view;
    private EditText etCategoryName;
    private ImageButton btnCreateCategory;
    private ImageButton btnRemoveColor;
    private ImageView ivSelectedColor;
    private ImageView ivColor1, ivColor2, ivColor3, ivColor4, ivColor5, ivColor6;
    private GridLayout colorSelectionLayout;

    /*|*******************************************************
                       Constructor Methods
    *********************************************************/
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        view = inflater.inflate(R.layout.dialog_create_category, container, false);

        findAllViews();
        setupButtonsFunctionality();
        setupColorButtonsFunctionality();

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
        colorSelectionLayout = view.findViewById(R.id.colorSelectionLayout);
        etCategoryName = view.findViewById(R.id.etCategoryName);
        btnCreateCategory = view.findViewById(R.id.btnCreateCategory);
        btnRemoveColor = view.findViewById(R.id.btnRemoveColor);
        ivSelectedColor = view.findViewById(R.id.ivSelectedColor);
        ivColor1 = view.findViewById(R.id.ivColor1);
        ivColor2 = view.findViewById(R.id.ivColor2);
        ivColor3 = view.findViewById(R.id.ivColor3);
        ivColor4 = view.findViewById(R.id.ivColor4);
        ivColor5 = view.findViewById(R.id.ivColor5);
        ivColor6 = view.findViewById(R.id.ivColor6);
    }

    private void setupButtonsFunctionality() {
        btnCreateCategory.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                String categoryName = etCategoryName.getText().toString().trim();
                if( !categoryName.isEmpty() ) {
                    Category category = new Category(categoryName);
                    category.setMainColor(selectedColor);

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

        btnRemoveColor.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                selectedColor = getColorHex(R.color.color_6);
                updateSelectedColorImageView();
            }
        });

    }

    private void setupColorButtonsFunctionality() {
        ivColor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColorHex(R.color.color_1);
                updateSelectedColorImageView();
            }
        });


        ivColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColorHex(R.color.color_2);
                updateSelectedColorImageView();
            }
        });

        ivColor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColorHex(R.color.color_3);
                updateSelectedColorImageView();;
            }
        });

        ivColor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColorHex(R.color.color_4);
                updateSelectedColorImageView();
            }
        });

        ivColor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColorHex(R.color.color_5);
                updateSelectedColorImageView();
            }
        });

        ivColor6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColorHex(R.color.color_6);
                updateSelectedColorImageView();
            }
        });
    }

    private String getColorHex( int colorResId ) {
        return String.format("#%06X", (0xFFFFFF & getResources().getColor(colorResId)));
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

    /*|*******************************************************
                         Color Methods
    *********************************************************/
    /*
        ` Adds the highlighted background to the selected color. Also removes the highlighted
        background from the previously selected color through removeColorHighlight();
     */
    private void updateSelectedColorImageView() {
        int colorValue = Color.parseColor(selectedColor);
        ColorStateList colorStateList = ColorStateList.valueOf(colorValue);
        ImageViewCompat.setImageTintList(ivSelectedColor, colorStateList);
    }

}