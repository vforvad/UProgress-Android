package com.example.vsokoltsov.uprogress.models.authorization;

import com.example.vsokoltsov.uprogress.models.authorization.SignIn.SignInRequest;
import com.example.vsokoltsov.uprogress.models.authorization.SignUp.SignUpRequest;

import rx.Observable;

/**
 * Created by vsokoltsov on 24.12.16.
 */

public interface AuthenticationModel {
    Observable<Token> signInRequest(SignInRequest request);
    Observable<Token> signUpRequest(SignUpRequest request);
    Observable<CurrentUser> getCurrentUser();
}
