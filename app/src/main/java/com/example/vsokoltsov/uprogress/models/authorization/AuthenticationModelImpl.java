package com.example.vsokoltsov.uprogress.models.authorization;

import android.content.Context;

import com.example.vsokoltsov.uprogress.api.UserApi;
import com.example.vsokoltsov.uprogress.helpers.PreferencesHelper;
import com.example.vsokoltsov.uprogress.models.BaseModelImpl;
import com.example.vsokoltsov.uprogress.models.authorization.CurrentUser;
import com.example.vsokoltsov.uprogress.models.authorization.AuthenticationModel;
import com.example.vsokoltsov.uprogress.models.authorization.SignIn.SignInRequest;
import com.example.vsokoltsov.uprogress.models.authorization.SignUp.SignUpRequest;
import com.example.vsokoltsov.uprogress.models.authorization.Token;
import com.example.vsokoltsov.uprogress.utils.ApiRequester;
import com.example.vsokoltsov.uprogress.view_holders.SignInViewHolder;
import com.example.vsokoltsov.uprogress.view_holders.SignUpViewHolder;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by vsokoltsov on 24.12.16.
 */

// TODO make this class singleton and move initialization into contructor
public class AuthenticationModelImpl extends BaseModelImpl implements AuthenticationModel {
    private SignInViewHolder signInViewHolder;
    private SignUpViewHolder signUpViewHolder;
    private UserApi service;

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
        service = retrofit.create(UserApi.class);
    }
}
