package com.example.vsokoltsov.uprogress.presenters;

import android.support.v4.app.Fragment;

import com.example.vsokoltsov.uprogress.helpers.PreferencesHelper;
import com.example.vsokoltsov.uprogress.models.authorization.CurrentUser;
import com.example.vsokoltsov.uprogress.models.authorization.AuthenticationModel;
import com.example.vsokoltsov.uprogress.models.authorization.Token;
import com.example.vsokoltsov.uprogress.ui.ApplicationBaseActivity;
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
//        activity.startProgressBar();
        model.signInRequest()
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
//                        activity.stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        try {
////                            view.failedResponse(e);
////                            activity.stopProgressBar();
//                        } catch (IOException e1) {
//                            e1.printStackTrace();
//                        }
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
//        activity.startProgressBar();
        model.signUpRequest()
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
                       screen.onSingUpDone();
                    }

                    @Override
                    public void onError(Throwable e) {
                        screen.failedResponse(e);
////
                    }

                    @Override
                    public void onNext(CurrentUser currentUser) {
                        // TODO Add redirect to user's profile
                        screen.successResponse(currentUser);
                    }
                });
    }
}
