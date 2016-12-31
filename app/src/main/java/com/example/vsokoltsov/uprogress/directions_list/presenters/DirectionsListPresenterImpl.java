package com.example.vsokoltsov.uprogress.directions_list.presenters;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.example.vsokoltsov.uprogress.authentication.models.User;
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

    private int pageNumber = 1;
    private ApplicationBaseActivity activity;
    private SwipeRefreshLayout layout;
    private static int firstVisibleInListview;

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

    @Override
    public void refreshList() {
        layout = view.getRefreshLayout(); // should not be here
        pageNumber = 1;
        layout.setRefreshing(true);
        activity.startProgressBar();
        model.getDirectionsList(user.getNick(), pageNumber)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DirectionsList>() {
                    @Override
                    public void onCompleted() {
                        layout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            view.failedResponse(e);
                            layout.setRefreshing(false);
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
    public void scrollDownListener(RecyclerView recyclerView, int dx, int dy) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int lastLayoutPosition = layoutManager.findLastCompletelyVisibleItemPosition();
        int itemsCount = layoutManager.getItemCount() - 2;
        if (lastLayoutPosition == itemsCount && dy > 0) {
            pageNumber++;
            loadDirections();
        }
    }
}
