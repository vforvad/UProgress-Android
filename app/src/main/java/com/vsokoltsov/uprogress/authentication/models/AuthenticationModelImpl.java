package com.vsokoltsov.uprogress.authentication.models;

import android.content.Context;

import com.vsokoltsov.uprogress.authentication.network.AuthenticationApi;
import com.vsokoltsov.uprogress.common.BaseModelImpl;
import com.vsokoltsov.uprogress.authentication.models.SignIn.SignInRequest;
import com.vsokoltsov.uprogress.authentication.models.SignUp.SignUpRequest;
import com.vsokoltsov.uprogress.user.current.CurrentUser;
import com.vsokoltsov.uprogress.user.current.network.CurrentUserApi;

import rx.Observable;

/**
 * Created by vsokoltsov on 24.12.16.
 */

// TODO make this class singleton and move initialization into contructor
public class AuthenticationModelImpl extends BaseModelImpl implements AuthenticationModel {
    private AuthenticationApi service;
    private CurrentUserApi currentUserService;

    public AuthenticationModelImpl(Context context) {
        super(context);
        setService();
    }

    @Override
    public Observable<Token> signInRequest(SignInRequest request) {
        return service.signIn(request);
    }


    //TODO: Move this to the presenter and have the view pass the text of the email field and
    // password as parameters and let the presenter do the requests
    @Override
    public Observable<Token> signUpRequest(SignUpRequest request) {
        return service.signUp(request);
    }

    @Override
    public Observable<CurrentUser> getCurrentUser() {
        return currentUserService.getCurrentUser();
    }

    private void setService() {
        service = retrofit.create(AuthenticationApi.class);
        currentUserService = retrofit.create(CurrentUserApi.class);
    }
}
