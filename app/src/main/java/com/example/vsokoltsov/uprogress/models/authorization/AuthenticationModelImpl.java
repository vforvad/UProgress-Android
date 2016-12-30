package com.example.vsokoltsov.uprogress.models.authorization;

import android.content.Context;

import com.example.vsokoltsov.uprogress.api.UserApi;
import com.example.vsokoltsov.uprogress.helpers.PreferencesHelper;
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
public class AuthenticationModelImpl implements AuthenticationModel {
    private SignInViewHolder signInViewHolder;
    private SignUpViewHolder signUpViewHolder;

    public AuthenticationModelImpl(SignInViewHolder signInViewHolder) {
        this.signInViewHolder = signInViewHolder;
    }

    public AuthenticationModelImpl(SignUpViewHolder signUpViewHolder) {
        this.signUpViewHolder = signUpViewHolder;
    }

    @Override
    public Observable<Token> signInRequest() {
        SignInRequest request = new SignInRequest(
                signInViewHolder.emailField.getText().toString(),
                signInViewHolder.passwordField.getText().toString()
        );
        Context context = signUpViewHolder.emailField.getContext(); //TODO:get the context with a better mecanism than this
        PreferencesHelper helper = new PreferencesHelper(context);
        Retrofit retrofit = ApiRequester.getInstance().getRestAdapter(helper);
        UserApi service = retrofit.create(UserApi.class);
        return service.signIn(request);
    }


    //TODO: Move this to the presenter and have the view pass the text of the email field and
    // password as parameters and let the presenter do the requests
    @Override
    public Observable<Token> signUpRequest() {
        SignUpRequest request = new SignUpRequest(
                signUpViewHolder.emailField.getText().toString(),
                signUpViewHolder.passwordField.getText().toString(),
                signUpViewHolder.passwordConfirmationField.getText().toString(),
                signUpViewHolder.nickField.getText().toString()
        );
        Context context = signUpViewHolder.emailField.getContext(); //TODO:get the context with a better mecanism than this
        PreferencesHelper helper = new PreferencesHelper(context);
        Retrofit retrofit = ApiRequester.getInstance().getRestAdapter(helper);
        UserApi service = retrofit.create(UserApi.class);
        return service.signUp(request);
    }

    @Override
    public Observable<CurrentUser> getCurrentUser() {
        Context context = signUpViewHolder.emailField.getContext(); //TODO:get the context with a better mecanism than this
        PreferencesHelper helper = new PreferencesHelper(context);
        Retrofit retrofit = ApiRequester.getInstance().getRestAdapter(helper);
        UserApi service = retrofit.create(UserApi.class);
        return service.getCurrentUser();
    }
}
