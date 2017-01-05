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
import android.widget.EditText;

import com.example.vsokoltsov.uprogress.R;

/**
 * Created by vsokoltsov on 05.01.17.
 */

public class AddStepForm extends DialogFragment {
    PopupInterface popupInterface;
    EditText stepDescription;

    public void setPopupInterface(PopupInterface popupInterface) {
        this.popupInterface = popupInterface;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_step_popup, container, false);
        DisplayMetrics dm = new DisplayMetrics();
        getDialog().getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        stepDescription = (EditText) rootView.findViewById(R.id.stepDescription);
        stepDescription.setMaxHeight((int)(height * 0.6));
        return rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
