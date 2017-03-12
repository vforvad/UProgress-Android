package com.vsokoltsov.uprogress.directions_list.popup;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.direction_detail.model.steps.StepRequest;
import com.vsokoltsov.uprogress.direction_detail.popup.PopupInterface;
import com.vsokoltsov.uprogress.directions_list.models.Direction;
import com.vsokoltsov.uprogress.directions_list.models.DirectionRequest;

/**
 * Created by vsokoltsov on 06.01.17.
 */

public class DirectionsListPopup extends DialogFragment {
    PopupInterface popupInterface;
    View rootView;
    public EditText directionTitle;
    public EditText directionDescription;
    public TextInputLayout titleWrapper;
    public TextInputLayout descriptionWrapper;
    Direction direction = null;
    Button submitDirection;

    public void setPopupInterface(PopupInterface popupInterface) {
        this.popupInterface = popupInterface;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.add_direction_layout, container, false);
        Bundle arguments = getArguments();
        if (arguments != null) {
            direction = arguments.getParcelable("direction");
        }
        setElements();
        setMaxHeightToStepDescription();
        return rootView;
    }

    private void setElements() {
        directionTitle = (EditText) rootView.findViewById(R.id.directionTitle);
        directionDescription = (EditText) rootView.findViewById(R.id.directionDescription);

        if (direction != null) {
            directionTitle.setText(direction.getTitle());
            directionDescription.setText(direction.getDescription());
        }

        titleWrapper = (TextInputLayout) rootView.findViewById(R.id.titleWrapper);
        descriptionWrapper = (TextInputLayout) rootView.findViewById(R.id.descriptionWrapper);

        submitDirection = (Button)  rootView.findViewById(R.id.submitDirection);
        submitDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean operation;
                DirectionRequest request = new DirectionRequest(
                        directionTitle.getText().toString(),
                        directionDescription.getText().toString()
                );
                if (direction != null) {
                    operation = false;
                }
                else {
                    operation = true;
                }
                popupInterface.successPopupOperation(request, operation);
            }
        });

    }

    private void setMaxHeightToStepDescription() {
        DisplayMetrics dm = new DisplayMetrics();
        getDialog().getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        directionDescription.setMaxHeight((int)(height * 0.2));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
