package com.example.vsokoltsov.uprogress.presenters.directions;

import android.support.v4.app.Fragment;

import com.example.vsokoltsov.uprogress.models.User;
import com.example.vsokoltsov.uprogress.models.authorization.CurrentUser;
import com.example.vsokoltsov.uprogress.models.authorization.Token;
import com.example.vsokoltsov.uprogress.models.directions.DirectionModel;
import com.example.vsokoltsov.uprogress.models.directions.DirectionsList;
import com.example.vsokoltsov.uprogress.ui.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.views.directions.DirectionsListView;

import java.io.IOException;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 29.12.16.
 */

public class DirectionsListPresenterImpl implements DirectionsListPresenter {
    private final DirectionsListView view;
    private final DirectionModel model;
    private final User user;
    private int pageNumber = 1;
    private ApplicationBaseActivity activity;

    public DirectionsListPresenterImpl(DirectionsListView view, DirectionModel model, User user) {
        this.view = view;
        this.model = model;
        this.user = user;
    }

    @Override
    public void onCreate(ApplicationBaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public void loadDirections() {
        activity.startProgressBar();
        model.getDirectionsList(user.getNick(), pageNumber)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DirectionsList>() {
                    @Override
                    public void onCompleted() {
                        activity.stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            view.failedResponse(e);
                            activity.stopProgressBar();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(DirectionsList list) {
                        view.successResponse(list);
                    }
                });
    }
}
