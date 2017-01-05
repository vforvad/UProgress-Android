package com.example.vsokoltsov.uprogress.direction_detail.popup;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.direction_detail.model.steps.Step;

/**
 * Created by vsokoltsov on 05.01.17.
 */

public class StepDialogFragment extends DialogFragment {
    PopupInterface popupInterface;
    private Step step;
    private TextView stepTitle;
    private TextView stepDescription;
    private TextView stepUpdatedAt;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.popup_window, container, false);
        setCancelable(true);
        setElements(rootView);
        setValues();
        return rootView;
    }

    private void setElements(View rootView) {
        stepTitle = (TextView) rootView.findViewById(R.id.stepTitle);
        stepDescription = (TextView) rootView.findViewById(R.id.stepDescription);
        stepUpdatedAt = (TextView) rootView.findViewById(R.id.stepUpdatedAt);
    }

    private void setValues() {
        stepTitle.setText(step.getTitle());
        stepDescription.setText(step.getDescription());
        if (step.getUpdatedAt() != null) {
            long now = System.currentTimeMillis();
            String date = (String) DateUtils.getRelativeTimeSpanString(step.getUpdatedAt().getTime(), now, DateUtils.DAY_IN_MILLIS);
            stepUpdatedAt.setText(date);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public void setPopupInterface(PopupInterface popupInterface) {
        this.popupInterface = popupInterface;
    }
}
