package com.example.vsokoltsov.uprogress.views;

import com.example.vsokoltsov.uprogress.models.authorization.CurrentUser;
import com.example.vsokoltsov.uprogress.services.ErrorResponse;
import com.example.vsokoltsov.uprogress.utils.RetrofitException;
import com.example.vsokoltsov.uprogress.view_holders.SignUpViewHolder;
import com.example.vsokoltsov.uprogress.views.authorization.AuthorizationScreen;

import java.io.IOException;

/**
 * Created by vsokoltsov on 26.12.16.
 */

public class SignUpView extends AuthView implements AuthorizationScreen {
    private final SignUpViewHolder viewHolder;

    public SignUpView(SignUpViewHolder viewHolder) {
        this.viewHolder = viewHolder;
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
    public void onSingUpDone() {

    }
}
