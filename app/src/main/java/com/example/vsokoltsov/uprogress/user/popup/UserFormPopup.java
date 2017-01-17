package com.example.vsokoltsov.uprogress.user.popup;

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
import com.example.vsokoltsov.uprogress.direction_detail.popup.PopupInterface;
import com.example.vsokoltsov.uprogress.user.current.User;
import com.example.vsokoltsov.uprogress.user.current.UserRequest;

/**
 * Created by vsokoltsov on 10.01.17.
 */

public class UserFormPopup extends DialogFragment implements View.OnClickListener {
    PopupInterface popupInterface;
    View rootView;
    private User user;
    public EditText firstNameField;
    public EditText secondNameField;
    public EditText emailField;
    public EditText descriptionField;
    public EditText locationField;
    Button submitForm;

    public void setPopupInterface(PopupInterface popupInterface) {
        this.popupInterface = popupInterface;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.user_form, container, false);
        setElements();
        setMaxHeightToStepDescription();
        setValues();
        return rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return dialog;
    }

    private void setMaxHeightToStepDescription() {
        DisplayMetrics dm = new DisplayMetrics();
        getDialog().getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        descriptionField.setMaxHeight((int)(height * 0.1));
    }

    private void setElements() {
        firstNameField = (EditText) rootView.findViewById(R.id.firstNameField);
        secondNameField = (EditText) rootView.findViewById(R.id.lastNameField);
        emailField = (EditText) rootView.findViewById(R.id.emailField);
        locationField = (EditText) rootView.findViewById(R.id.locationField);
        descriptionField = (EditText) rootView.findViewById(R.id.descriptionField);
        submitForm = (Button) rootView.findViewById(R.id.submitForm);

        submitForm.setOnClickListener(this);
    }

    private void setValues() {
        firstNameField.setText(user.getFirstName());
        secondNameField.setText(user.getLastName());
        emailField.setText(user.getEmail());
        locationField.setText(user.getLocation());
        descriptionField.setText(user.getDescription());
    }

    @Override
    public void onClick(View v) {
        UserRequest request = new UserRequest(
                firstNameField.getText().toString(),
                secondNameField.getText().toString(),
                emailField.getText().toString(),
                locationField.getText().toString(),
                descriptionField.getText().toString()
        );
        popupInterface.successPopupOperation(request);
    }
}
