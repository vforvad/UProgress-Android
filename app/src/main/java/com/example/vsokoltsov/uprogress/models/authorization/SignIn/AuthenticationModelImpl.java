package com.example.vsokoltsov.uprogress.models.authorization.SignIn;

import com.example.vsokoltsov.uprogress.api.UserApi;
import com.example.vsokoltsov.uprogress.models.authorization.CurrentUser;
import com.example.vsokoltsov.uprogress.models.authorization.AuthenticationModel;
import com.example.vsokoltsov.uprogress.models.authorization.Token;
import com.example.vsokoltsov.uprogress.utils.ApiRequester;
import com.example.vsokoltsov.uprogress.view_holders.SignInViewHolder;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by vsokoltsov on 24.12.16.
 */

public class AuthenticationModelImpl implements AuthenticationModel {
    private final SignInViewHolder signInViewHolder;

    public AuthenticationModelImpl(SignInViewHolder signInViewHolder) {
        this.signInViewHolder = signInViewHolder;
    }

    @Override
    public Observable<Token> signInRequest() {
        SignInRequest request = new SignInRequest(
                signInViewHolder.emailField.getText().toString(),
                signInViewHolder.passwordField.getText().toString()
        );
        Retrofit retrofit = ApiRequester.getInstance().getRestAdapter();
        UserApi service = retrofit.create(UserApi.class);
        return service.signIn(request);
    }

    @Override
    public Observable<CurrentUser> getCurrentUser() {
        Retrofit retrofit = ApiRequester.getInstance().getRestAdapter();
        UserApi service = retrofit.create(UserApi.class);
        return service.getCurrentUser();
    }
}