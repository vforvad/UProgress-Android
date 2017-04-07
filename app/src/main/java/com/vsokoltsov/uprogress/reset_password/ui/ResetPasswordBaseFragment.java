package com.vsokoltsov.uprogress.reset_password.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.authentication.ui.AuthorizationActivity;
import com.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.vsokoltsov.uprogress.common.BaseApplication;
import com.vsokoltsov.uprogress.common.ErrorHandler;
import com.vsokoltsov.uprogress.common.NavigationFragments;
import com.vsokoltsov.uprogress.common.TabletActivity;
import com.vsokoltsov.uprogress.common.TabletFragments;
import com.vsokoltsov.uprogress.common.services.ErrorResponse;
import com.vsokoltsov.uprogress.common.utils.RetrofitException;
import com.vsokoltsov.uprogress.reset_password.model.ResetPasswordModel;
import com.vsokoltsov.uprogress.reset_password.model.ResetPasswordModelImpl;
import com.vsokoltsov.uprogress.reset_password.model.ResetPasswordRequest;
import com.vsokoltsov.uprogress.reset_password.presenter.ResetPasswordPresenter;
import com.vsokoltsov.uprogress.reset_password.presenter.ResetPasswordPresenterImpl;
import com.vsokoltsov.uprogress.reset_password.view.ResetPasswordView;

import java.io.IOException;


/**
 * Created by vsokoltsov on 07.04.17.
 */

public class ResetPasswordBaseFragment extends Fragment implements Button.OnClickListener, ResetPasswordView {
    private View fragmentView;
    private String token;
    private boolean isTablet;
    private ApplicationBaseActivity activity;
    private EditText passwordField;
    private EditText passwordConfirmationField;
    private Button resetPasswordButton;
    private ResetPasswordPresenter presenter;
    private TabletFragments tabletFragments;
    private NavigationFragments navigationFragments;
    ErrorHandler errorHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.reset_password_fragment, container, false);
        setFields();
        setButton();
        activity = (ApplicationBaseActivity) getActivity();
        isTablet = getResources().getBoolean(R.bool.isTablet);
        tabletFragments = new TabletFragments(((ApplicationBaseActivity) getContext()).getSupportFragmentManager());
        navigationFragments = new NavigationFragments(getContext());
        errorHandler = new ErrorHandler(getActivity());
        if (isTablet) {
            try {
                ((TabletActivity) getActivity()).setToolbar();
                ((TabletActivity) getActivity()).getToolBar().setTitle(getResources().getString(R.string.reset_password));
            }
            catch(Exception e) {
                e.printStackTrace();
            }

        }
        else {
            activity.setTitle(getResources().getString(R.string.reset_password));
        }
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        receiveToken();
        ResetPasswordModel model = new ResetPasswordModelImpl(getContext());
        presenter = new ResetPasswordPresenterImpl(model, this);

        return fragmentView;
    }

    private void setButton() {
        resetPasswordButton = (Button) fragmentView.findViewById(R.id.resetPasswordButton);
        resetPasswordButton.setOnClickListener(this);
    }

    private void setFields() {
        passwordField = (EditText) fragmentView.findViewById(R.id.passwordField);
        passwordConfirmationField = (EditText) fragmentView.findViewById(R.id.passwordConfirmationField);

        Drawable passwordImg = ContextCompat.getDrawable(getContext(), R.drawable.password);

        passwordField.setCompoundDrawablesWithIntrinsicBounds( passwordImg, null, null, null);
        passwordConfirmationField.setCompoundDrawablesWithIntrinsicBounds( passwordImg, null, null, null);
    }

    private void receiveToken() {
        Bundle extras = getArguments();
        token = extras.getString("reset_token");
    }

    @Override
    public void onClick(View view) {
        ResetPasswordRequest request = new ResetPasswordRequest(
                passwordField.getText().toString(),
                passwordConfirmationField.getText().toString(),
                token
        );
        presenter.resetPassword(request);
    }

    @Override
    public void successReset(String message) {
        if (isTablet) {
            tabletFragments.shoAuthorizationProfile("sign_in", message);
        }
        else {
            navigationFragments.signIn("sign_in", message);
        }
    }

    @Override
    public void failedReset(Throwable t) {
        RetrofitException error = (RetrofitException) t;
        try {
            ErrorResponse errors = null;
            errors = error.getErrorBodyAs(ErrorResponse.class);
            passwordField.setError(errors.getFullErrorMessage("password"));
            passwordConfirmationField.setError(errors.getFullErrorMessage("password_confirmation"));
        } catch (IOException e) {
            errorHandler.showMessage(t);
        }
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
