package com.example.vsokoltsov.uprogress.views;

import com.example.vsokoltsov.uprogress.models.authorization.Token;
import com.example.vsokoltsov.uprogress.services.ErrorResponse;
import com.example.vsokoltsov.uprogress.utils.RetrofitException;
import com.example.vsokoltsov.uprogress.view_holders.SignInViewHolder;

import java.io.IOException;

/**
 * Created by vsokoltsov on 24.12.16.
 */

public class SignInViewImpl implements SignInView {
    private final SignInViewHolder viewHolder;

    public SignInViewImpl(SignInViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }

    @Override
    public void successResponse(Token token) {

    }

    @Override
    public void failedResponse(Throwable t) throws IOException {
        RetrofitException error = (RetrofitException) t;
        ErrorResponse errors = error.getErrorBodyAs(ErrorResponse.class);
        viewHolder.emailField.setError(errors.getFullErrorMessage("email"));
        viewHolder.passwordField.setError(errors.getFullErrorMessage("password"));
    }
}
