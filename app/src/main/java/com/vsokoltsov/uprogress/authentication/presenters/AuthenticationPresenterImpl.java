package com.vsokoltsov.uprogress.authentication.presenters;

import com.vsokoltsov.uprogress.common.helpers.PreferencesHelper;
import com.vsokoltsov.uprogress.user.current.CurrentUser;
import com.vsokoltsov.uprogress.authentication.models.AuthenticationModel;
import com.vsokoltsov.uprogress.authentication.models.SignIn.SignInRequest;
import com.vsokoltsov.uprogress.authentication.models.SignUp.SignUpRequest;
import com.vsokoltsov.uprogress.authentication.models.Token;
import com.vsokoltsov.uprogress.authentication.views.AuthorizationScreen;

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
    public void onSignInSubmit(SignInRequest request) {
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
    public void onSignUpSubmit(SignUpRequest request) {
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
