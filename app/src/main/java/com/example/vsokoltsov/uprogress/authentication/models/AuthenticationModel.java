package com.example.vsokoltsov.uprogress.authentication.models;

import com.example.vsokoltsov.uprogress.authentication.models.SignIn.SignInRequest;
import com.example.vsokoltsov.uprogress.authentication.models.SignUp.SignUpRequest;
import com.example.vsokoltsov.uprogress.user.current.CurrentUser;

import rx.Observable;

/**
 * Created by vsokoltsov on 24.12.16.
 */

public interface AuthenticationModel {
    Observable<Token> signInRequest(SignInRequest request);
    Observable<Token> signUpRequest(SignUpRequest request);
    Observable<CurrentUser> getCurrentUser();
}
