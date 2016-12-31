package com.example.vsokoltsov.uprogress.authentication.models;

import com.example.vsokoltsov.uprogress.authentication.network.AuthenticationApi;
import com.example.vsokoltsov.uprogress.common.BaseModelImpl;
import com.example.vsokoltsov.uprogress.authentication.models.SignIn.SignInRequest;
import com.example.vsokoltsov.uprogress.authentication.models.SignUp.SignUpRequest;
import com.example.vsokoltsov.uprogress.authentication.view_holders.SignInViewHolder;
import com.example.vsokoltsov.uprogress.authentication.view_holders.SignUpViewHolder;

import rx.Observable;

/**
 * Created by vsokoltsov on 24.12.16.
 */

// TODO make this class singleton and move initialization into contructor
public class AuthenticationModelImpl extends BaseModelImpl implements AuthenticationModel {
    private SignInViewHolder signInViewHolder;
    private SignUpViewHolder signUpViewHolder;
    private AuthenticationApi service;

    public AuthenticationModelImpl(SignInViewHolder signInViewHolder) {
        super();
        this.signInViewHolder = signInViewHolder;
        setService();
    }

    public AuthenticationModelImpl(SignUpViewHolder signUpViewHolder) {
        super();
        this.signUpViewHolder = signUpViewHolder;
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
        return service.getCurrentUser();
    }

    private void setService() {
        service = retrofit.create(AuthenticationApi.class);
    }
}
