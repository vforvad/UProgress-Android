package com.example.vsokoltsov.uprogress.authentication.models;

import com.example.vsokoltsov.uprogress.authentication.models.SignIn.SignInRequest;
import com.example.vsokoltsov.uprogress.authentication.models.SignUp.SignUpRequest;
import com.example.vsokoltsov.uprogress.user.current.CurrentUser;
import com.example.vsokoltsov.uprogress.user.current.CurrentUserModel;

import rx.Observable;

/**
 * Created by vsokoltsov on 24.12.16.
 */

public interface AuthenticationModel extends CurrentUserModel {
    Observable<Token> signInRequest(SignInRequest request);
    Observable<Token> signUpRequest(SignUpRequest request);
}
