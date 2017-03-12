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
import com.vsokoltsov.uprogress.authentication.messages.UserMessage;
import com.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.vsokoltsov.uprogress.authentication.models.SignUp.SignUpRequest;
import com.vsokoltsov.uprogress.common.ErrorHandler;
import com.vsokoltsov.uprogress.common.helpers.PreferencesHelper;
import com.vsokoltsov.uprogress.authentication.models.AuthenticationModel;
import com.vsokoltsov.uprogress.authentication.models.AuthenticationModelImpl;
import com.vsokoltsov.uprogress.authentication.presenters.AuthenticationPresenter;
import com.vsokoltsov.uprogress.authentication.presenters.AuthenticationPresenterImpl;
import com.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.vsokoltsov.uprogress.authentication.views.AuthorizationScreen;
import com.vsokoltsov.uprogress.common.services.ErrorResponse;
import com.vsokoltsov.uprogress.common.utils.RetrofitException;
import com.vsokoltsov.uprogress.user.current.CurrentUser;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

/**
 * Created by vsokoltsov on 22.11.16.
 */

public class SignUpFragment extends Fragment implements Button.OnClickListener, AuthorizationScreen {
    private View fragmentView;
    public ApplicationBaseActivity activity;
    private EditText emailField;
    private EditText passwordField;
    private EditText passwordConfirmationField;
    private EditText nickField;
    private AuthenticationPresenter presenter;
    private AuthorizationService auth = AuthorizationService.getInstance();
    ErrorHandler errorHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (ApplicationBaseActivity) getActivity();
        fragmentView = inflater.inflate(R.layout.sign_up_fragment, container, false);
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
        passwordConfirmationField = (EditText) fragmentView.findViewById(R.id.passwordConfirmationField);
        nickField = (EditText) fragmentView.findViewById(R.id.nickField);

        Drawable emailImg = ContextCompat.getDrawable(getContext(), R.drawable.email);
        Drawable passwordImg = ContextCompat.getDrawable(getContext(), R.drawable.password);
        Drawable nickImg = ContextCompat.getDrawable(getContext(), R.drawable.user_nick);

        emailField.setCompoundDrawablesWithIntrinsicBounds( emailImg, null, null, null);
        passwordField.setCompoundDrawablesWithIntrinsicBounds( passwordImg, null, null, null);
        passwordConfirmationField.setCompoundDrawablesWithIntrinsicBounds( passwordImg, null, null, null);
        nickField.setCompoundDrawablesWithIntrinsicBounds( nickImg, null, null, null);
    }

    private void setButton() {
        Button signUpButton = (Button) fragmentView.findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SignUpRequest request = new SignUpRequest(
                emailField.getText().toString(),
                passwordField.getText().toString(),
                passwordConfirmationField.getText().toString(),
                nickField.getText().toString()
        );
        presenter.onSignUpSubmit(request);
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
            passwordConfirmationField.setError(errors.getFullErrorMessage("password_confirmation"));
            nickField.setError(errors.getFullErrorMessage("nick"));
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
