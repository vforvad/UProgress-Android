package com.example.vsokoltsov.uprogress.models.authorization;

import com.example.vsokoltsov.uprogress.models.authorization.SignIn.SignInRequest;

import rx.Observable;

/**
 * Created by vsokoltsov on 24.12.16.
 */

public interface AuthenticationModel {
    Observable<Token> signInRequest();
    Observable<Token> signUpRequest();
    Observable<CurrentUser> getCurrentUser();
}
