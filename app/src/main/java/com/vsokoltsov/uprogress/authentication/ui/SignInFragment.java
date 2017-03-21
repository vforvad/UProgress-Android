package com.vsokoltsov.uprogress.authentication.ui;

import android.content.pm.PackageInfo;
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
import com.vsokoltsov.uprogress.authentication.messages.UserMessage;
import com.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.vsokoltsov.uprogress.authentication.models.SignIn.SignInRequest;
import com.vsokoltsov.uprogress.common.BaseApplication;
import com.vsokoltsov.uprogress.common.ErrorDialog;
import com.vsokoltsov.uprogress.common.ErrorHandler;
import com.vsokoltsov.uprogress.common.helpers.PreferencesHelper;
import com.vsokoltsov.uprogress.authentication.models.AuthenticationModelImpl;
import com.vsokoltsov.uprogress.authentication.models.AuthenticationModel;
import com.vsokoltsov.uprogress.authentication.presenters.AuthenticationPresenter;
import com.vsokoltsov.uprogress.authentication.presenters.AuthenticationPresenterImpl;
import com.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.vsokoltsov.uprogress.authentication.views.AuthorizationScreen;
import com.vsokoltsov.uprogress.common.services.ErrorResponse;
import com.vsokoltsov.uprogress.common.utils.DeviceTokenManager;
import com.vsokoltsov.uprogress.common.utils.RetrofitException;
import com.vsokoltsov.uprogress.user.current.CurrentUser;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

/**
 * Created by vsokoltsov on 22.11.16.
 */

public class SignInFragment extends Fragment implements Button.OnClickListener, AuthorizationScreen {
    BaseApplication baseApplication;
    private View fragmentView;
    public ApplicationBaseActivity activity;
    private EditText emailField;
    private EditText passwordField;
    private AuthenticationPresenter presenter;
    AuthorizationService auth = AuthorizationService.getInstance();
    ErrorHandler errorHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        baseApplication = ((BaseApplication) getActivity().getApplicationContext());
        activity = (ApplicationBaseActivity) getActivity();

        fragmentView = inflater.inflate(R.layout.sign_in_fragment, container, false);
        errorHandler = new ErrorHandler(getActivity());
        setFields();
        setButton();

        final AuthenticationModel model = new AuthenticationModelImpl(getActivity().getApplicationContext());
        presenter = new AuthenticationPresenterImpl(model, this, new PreferencesHelper(getContext()));
        return fragmentView;
    }

    private void setFields() {
        emailField = (EditText) fragmentView.findViewById(R.id.emailField);
        passwordField = (EditText) fragmentView.findViewById(R.id.passwordField);

        Drawable emailImg = ContextCompat.getDrawable(getContext(), R.drawable.email);
        Drawable passwordImg = ContextCompat.getDrawable(getContext(), R.drawable.password);

        emailField.setCompoundDrawablesWithIntrinsicBounds( emailImg, null, null, null);
        passwordField.setCompoundDrawablesWithIntrinsicBounds( passwordImg, null, null, null);
    }

    private void setButton() {
        Button button = (Button) fragmentView.findViewById(R.id.signInButton);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SignInRequest request = new SignInRequest(
                emailField.getText().toString(),
                passwordField.getText().toString(),
                getContext()
        );
        presenter.onSignInSubmit(request);
    }

    @Override
    public void successResponse(CurrentUser currentUser) {
        auth.setCurrentUser(currentUser.getUser());
        EventBus.getDefault().post(new UserMessage("currentUser", currentUser.getUser()));
        ((AuthorizationActivity) activity).redirectToProfile();
    }

    @Override
    public void failedResponse(Throwable t) {
        RetrofitException error = (RetrofitException) t;
        try {
            ErrorResponse errors = null;
            errors = error.getErrorBodyAs(ErrorResponse.class);
            emailField.setError(errors.getFullErrorMessage("email"));
            passwordField.setError(errors.getFullErrorMessage("password"));
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
