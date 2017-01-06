package com.example.vsokoltsov.uprogress.directions_list.popup;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.direction_detail.model.steps.StepRequest;
import com.example.vsokoltsov.uprogress.direction_detail.popup.PopupInterface;
import com.example.vsokoltsov.uprogress.directions_list.models.DirectionRequest;

/**
 * Created by vsokoltsov on 06.01.17.
 */

public class DirectionsListPopup extends DialogFragment {
    PopupInterface popupInterface;
    View rootView;
    EditText directionTitle;
    EditText directionDescription;
    Button submitDirection;

    public void setPopupInterface(PopupInterface popupInterface) {
        this.popupInterface = popupInterface;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.add_direction_layout, container, false);
        setElements();
        setMaxHeightToStepDescription();
        return rootView;
    }

    private void setElements() {
        directionTitle = (EditText) rootView.findViewById(R.id.directionTitle);
        directionDescription = (EditText) rootView.findViewById(R.id.directionDescription);
        submitDirection = (Button)  rootView.findViewById(R.id.submitDirection);
        submitDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DirectionRequest request = new DirectionRequest(
                        directionTitle.getText().toString(),
                        directionDescription.getText().toString()
                );
                popupInterface.successPopupOperation(request);
            }
        });

    }

    private void setMaxHeightToStepDescription() {
        DisplayMetrics dm = new DisplayMetrics();
        getDialog().getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        directionDescription.setMaxHeight((int)(height * 0.6));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
