package com.vsokoltsov.uprogress.authentication.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.authentication.models.AuthenticationModel;
import com.vsokoltsov.uprogress.authentication.models.AuthenticationModelImpl;
import com.vsokoltsov.uprogress.authentication.models.RestorePassword.RestorePasswordRequest;
import com.vsokoltsov.uprogress.authentication.presenters.AuthenticationPresenter;
import com.vsokoltsov.uprogress.authentication.presenters.AuthenticationPresenterImpl;
import com.vsokoltsov.uprogress.authentication.views.RestorePasswordScreen;
import com.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.vsokoltsov.uprogress.common.BaseApplication;
import com.vsokoltsov.uprogress.common.ErrorHandler;
import com.vsokoltsov.uprogress.common.helpers.PreferencesHelper;

/**
 * Created by vsokoltsov on 06.04.17.
 */

public class RestorePasswordFragment extends Fragment implements Button.OnClickListener, RestorePasswordScreen {
    private View fragmentView;
    public ApplicationBaseActivity activity;
    private EditText emailField;
    ErrorHandler errorHandler;
    private boolean isTablet;
    private AuthenticationModel model;
    private AuthenticationPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.restore_password, container, false);
        activity = (ApplicationBaseActivity) getActivity();
        isTablet = getResources().getBoolean(R.bool.isTablet);
        errorHandler = new ErrorHandler(getActivity());
        model = new AuthenticationModelImpl(getContext());
        presenter = new AuthenticationPresenterImpl(model, this);
        setFields();
        setButton();
        return fragmentView;
    }

    private void setFields() {
        emailField = (EditText) fragmentView.findViewById(R.id.emailField);

        Drawable emailImg = ContextCompat.getDrawable(getContext(), R.drawable.email);

        emailField.setCompoundDrawablesWithIntrinsicBounds( emailImg, null, null, null);
    }

    private void setButton() {
        Button button = (Button) fragmentView.findViewById(R.id.restorePasswordButton);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        RestorePasswordRequest request = new
                RestorePasswordRequest(emailField.getText().toString());
        presenter.onRestorePassword(request);
    }

    @Override
    public void successRestoreResponse(String token) {

    }

    @Override
    public void failedRestoreResponse(Throwable t) {

    }

    @Override
    public void startLoader() {
        activity.startProgressBar();
    }

    @Override
    public void stopLoader() {
        activity.stopProgressBar();
    }
}
