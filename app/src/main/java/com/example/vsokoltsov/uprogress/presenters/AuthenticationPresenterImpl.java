package com.example.vsokoltsov.uprogress.presenters;

import android.support.v4.app.Fragment;

import com.example.vsokoltsov.uprogress.models.authorization.CurrentUser;
import com.example.vsokoltsov.uprogress.models.authorization.AuthenticationModel;
import com.example.vsokoltsov.uprogress.models.authorization.Token;
import com.example.vsokoltsov.uprogress.views.authorization.AuthorizationView;

import java.io.IOException;

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
    private final AuthorizationView view;
    private Subscription subscription;

    public AuthenticationPresenterImpl(AuthenticationModel model, AuthorizationView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void onCreate(Fragment fragment) {

    }

    @Override
    public void onSignInSubmit() {
        model.signInRequest()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Token, Observable<CurrentUser>>() {
                    @Override
                    public Observable<CurrentUser> call(Token token) {
                        Token.writeToken(token.getToken());
                        return model.getCurrentUser()
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(new Observer<CurrentUser>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            view.failedResponse(e);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(CurrentUser currentUser) {
                        // TODO Add redirect to user's profile
                        view.successResponse(currentUser);
                    }
                });
    }

    @Override
    public void onSignUpSubmit() {
        model.signUpRequest()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Token, Observable<CurrentUser>>() {
                    @Override
                    public Observable<CurrentUser> call(Token token) {
                        Token.writeToken(token.getToken());
                        return model.getCurrentUser()
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(new Observer<CurrentUser>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            view.failedResponse(e);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(CurrentUser currentUser) {
                        // TODO Add redirect to user's profile
                        view.successResponse(currentUser);
                    }
                });
    }
}
