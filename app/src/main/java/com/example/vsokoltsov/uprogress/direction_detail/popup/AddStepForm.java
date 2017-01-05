package com.example.vsokoltsov.uprogress.direction_detail.popup;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.direction_detail.model.steps.StepRequest;

/**
 * Created by vsokoltsov on 05.01.17.
 */

public class AddStepForm extends DialogFragment {
    PopupInterface popupInterface;
    View rootView;
    EditText stepTitle;
    EditText stepDescription;
    Button submitStep;

    public void setPopupInterface(PopupInterface popupInterface) {
        this.popupInterface = popupInterface;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.add_step_popup, container, false);
        setElements();
        setMaxHeightToStepDescription();
        return rootView;
    }

    private void setElements() {
        stepTitle = (EditText) rootView.findViewById(R.id.stepTitle);
        stepDescription = (EditText) rootView.findViewById(R.id.stepDescription);
        submitStep = (Button)  rootView.findViewById(R.id.submitStep);
        submitStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StepRequest request = new StepRequest(
                        stepTitle.getText().toString(),
                        stepDescription.getText().toString(),
                        false);
                popupInterface.successPopupOperation(request);
            }
        });

    }

    private void setMaxHeightToStepDescription() {
        DisplayMetrics dm = new DisplayMetrics();
        getDialog().getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        stepDescription.setMaxHeight((int)(height * 0.6));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
