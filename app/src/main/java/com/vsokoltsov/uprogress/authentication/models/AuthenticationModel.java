package com.vsokoltsov.uprogress.authentication.models;

import com.vsokoltsov.uprogress.authentication.models.RestorePassword.RestorePasswordRequest;
import com.vsokoltsov.uprogress.authentication.models.SignIn.SignInRequest;
import com.vsokoltsov.uprogress.authentication.models.SignUp.SignUpRequest;
import com.vsokoltsov.uprogress.user.current.CurrentUser;
import com.vsokoltsov.uprogress.user.current.CurrentUserModel;

import rx.Observable;

/**
 * Created by vsokoltsov on 24.12.16.
 */

public interface AuthenticationModel extends CurrentUserModel {
    Observable<Token> signInRequest(SignInRequest request);
    Observable<Token> signUpRequest(SignUpRequest request);
    Observable<Token> restorePasswordRequest(RestorePasswordRequest request);
}
