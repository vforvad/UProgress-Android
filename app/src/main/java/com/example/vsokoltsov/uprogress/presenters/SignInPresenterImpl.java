package com.example.vsokoltsov.uprogress.presenters;

import android.support.v4.app.Fragment;

import com.example.vsokoltsov.uprogress.models.authorization.CurrentUser;
import com.example.vsokoltsov.uprogress.models.authorization.SignInModel;
import com.example.vsokoltsov.uprogress.models.authorization.Token;
import com.example.vsokoltsov.uprogress.views.SignInView;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 24.12.16.
 */

public class SignInPresenterImpl implements SignInPresenter {
    private final SignInModel model;
    private final SignInView view;
    private Subscription subscription;

    public SignInPresenterImpl(SignInModel model, SignInView view) {
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
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(CurrentUser currentUser) {
                        // TODO Add redirect to user's profile
                    }
                });
    }
}
