package com.example.vsokoltsov.uprogress.authentication.views;

import com.example.vsokoltsov.uprogress.authentication.models.CurrentUser;
import com.example.vsokoltsov.uprogress.common.services.ErrorResponse;
import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.common.utils.RetrofitException;
import com.example.vsokoltsov.uprogress.authentication.view_holders.SignUpViewHolder;

import java.io.IOException;

/**
 * Created by vsokoltsov on 26.12.16.
 */

public class SignUpView extends AuthView implements AuthorizationScreen {
    public final SignUpViewHolder viewHolder;
    public ApplicationBaseActivity activity;

    public SignUpView(SignUpViewHolder viewHolder, ApplicationBaseActivity activity) {
        this.viewHolder = viewHolder;
        this.activity = activity;
    }


    @Override
    public void successResponse(CurrentUser currentUser) {
        super.setCurrentUser(currentUser);
    }

    @Override
    public void failedResponse(Throwable t) {
        RetrofitException error = (RetrofitException) t;
        ErrorResponse errors = null;
        try {
            errors = error.getErrorBodyAs(ErrorResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewHolder.emailField.setError(errors.getFullErrorMessage("email"));
        viewHolder.passwordField.setError(errors.getFullErrorMessage("password"));
        viewHolder.passwordConfirmationField.setError(errors.getFullErrorMessage("password_confirmation"));
        viewHolder.nickField.setError(errors.getFullErrorMessage("nick"));
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
