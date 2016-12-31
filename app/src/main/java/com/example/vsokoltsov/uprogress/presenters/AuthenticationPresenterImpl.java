package com.example.vsokoltsov.uprogress.presenters;

import android.support.v4.app.Fragment;

import com.example.vsokoltsov.uprogress.helpers.PreferencesHelper;
import com.example.vsokoltsov.uprogress.models.authorization.CurrentUser;
import com.example.vsokoltsov.uprogress.models.authorization.AuthenticationModel;
import com.example.vsokoltsov.uprogress.models.authorization.SignIn.SignInRequest;
import com.example.vsokoltsov.uprogress.models.authorization.SignUp.SignUpRequest;
import com.example.vsokoltsov.uprogress.models.authorization.Token;
import com.example.vsokoltsov.uprogress.ui.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.view_holders.SignInViewHolder;
import com.example.vsokoltsov.uprogress.view_holders.SignUpViewHolder;
import com.example.vsokoltsov.uprogress.views.SignInView;
import com.example.vsokoltsov.uprogress.views.SignUpView;
import com.example.vsokoltsov.uprogress.views.authorization.AuthorizationScreen;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 24.12.16.
 */

public class AuthenticationPresenterImpl implements AuthenticationPresenter {
    private final AuthenticationModel model;
    private final AuthorizationScreen screen;
    private final PreferencesHelper preferencesHelper;
    private Subscription subscription;


    // Every component should consist in his own package

    public AuthenticationPresenterImpl(AuthenticationModel model, AuthorizationScreen screen, PreferencesHelper preferencesHelper) {
        this.model = model;
        this.screen = screen;
        this.preferencesHelper = preferencesHelper;
    }

  /*  @Override
    public void onCreate(Fragment fragment) {
        this.fragment = fragment;
        this.activity = (ApplicationBaseActivity) fragment.getActivity();
    }*/

    @Override
    public void onSignInSubmit() {
        SignInViewHolder viewHolder = ((SignInView) screen).viewHolder;
        SignInRequest request = new SignInRequest(
                viewHolder.emailField.getText().toString(),
                viewHolder.passwordField.getText().toString()
        );
        screen.startLoader();
        model.signInRequest(request)
                .flatMap(new Func1<Token, Observable<CurrentUser>>() {
                    @Override
                    public Observable<CurrentUser> call(Token token) {
                        preferencesHelper.writeToken(token.getToken());
                        return model.getCurrentUser();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CurrentUser>() {
                    @Override
                    public void onCompleted() {
                        screen.stopLoader();
                    }

                    @Override
                    public void onError(Throwable e) {
                        screen.failedResponse(e);
                        screen.stopLoader();
                    }

                    @Override
                    public void onNext(CurrentUser currentUser) {
                        // TODO Add redirect to user's profile
                        screen.successResponse(currentUser);
                    }
                });
    }

    @Override
    public void onSignUpSubmit() {
        SignUpViewHolder viewHolder = ((SignUpView) screen).viewHolder;
        SignUpRequest request = new SignUpRequest(
                viewHolder.emailField.getText().toString(),
                viewHolder.passwordField.getText().toString(),
                viewHolder.passwordConfirmationField.getText().toString(),
                viewHolder.nickField.getText().toString()
        );
        screen.startLoader();
        model.signUpRequest(request)
                .flatMap(new Func1<Token, Observable<CurrentUser>>() {
                    @Override
                    public Observable<CurrentUser> call(Token token) {
                        preferencesHelper.writeToken(token.getToken());
                        return model.getCurrentUser();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CurrentUser>() {
                    @Override
                    public void onCompleted() {
                       screen.stopLoader();
                    }

                    @Override
                    public void onError(Throwable e) {
                        screen.failedResponse(e);
                        screen.stopLoader();
                    }

                    @Override
                    public void onNext(CurrentUser currentUser) {
                        // TODO Add redirect to user's profile
                        screen.successResponse(currentUser);
                    }
                });
    }
}
