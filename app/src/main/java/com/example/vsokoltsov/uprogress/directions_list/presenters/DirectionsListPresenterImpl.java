package com.example.vsokoltsov.uprogress.directions_list.presenters;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.example.vsokoltsov.uprogress.user.User;
import com.example.vsokoltsov.uprogress.directions_list.models.DirectionModel;
import com.example.vsokoltsov.uprogress.directions_list.models.DirectionsList;
import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.directions_list.views.DirectionsListView;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.io.IOException;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 29.12.16.
 */

public class DirectionsListPresenterImpl implements DirectionsListPresenter {
    private final DirectionsListView view;
    private final DirectionModel model;
    private final User user;

    public int pageNumber = 1;

    public DirectionsListPresenterImpl(DirectionsListView view, DirectionModel model, User user) {
        this.view = view;
        this.model = model;
        this.user = user;
    }

    @Override
    public void loadDirections() {
        view.startLoader();
        model.getDirectionsList(user.getNick(), pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DirectionsList>() {
                    @Override
                    public void onCompleted() {
                        view.stopLoader();
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            view.failedResponse(e);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        view.stopLoader();
                    }

                    @Override
                    public void onNext(DirectionsList list) {
                        view.successResponse(list);
                    }
                });
    }

    @Override
    public void refreshList() {
        pageNumber = 1;
        view.startRefreshing();
        model.getDirectionsList(user.getNick(), pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DirectionsList>() {
                    @Override
                    public void onCompleted() {
                        view.stopRefreshing();
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            view.failedResponse(e);
                            view.stopRefreshing();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(DirectionsList list) {
                        view.refreshList(list);
                    }
                });
    }

    @Override
    public void loadMoreDirections() {
        pageNumber++;
        loadDirections();
    }
}
