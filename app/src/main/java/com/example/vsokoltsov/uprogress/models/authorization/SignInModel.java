package com.example.vsokoltsov.uprogress.models.authorization;

import com.example.vsokoltsov.uprogress.models.authorization.SignIn.SignInRequest;

import rx.Observable;

/**
 * Created by vsokoltsov on 24.12.16.
 */

public interface SignInModel {
    Observable<Token> signInRequest();
    Observable<CurrentUser> getCurrentUser();
}
