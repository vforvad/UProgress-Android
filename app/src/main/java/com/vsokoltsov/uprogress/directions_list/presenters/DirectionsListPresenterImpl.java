package com.vsokoltsov.uprogress.directions_list.presenters;

import com.vsokoltsov.uprogress.directions_list.models.DirectionRequest;
import com.vsokoltsov.uprogress.directions_list.models.DirectionResponse;
import com.vsokoltsov.uprogress.user.current.User;
import com.vsokoltsov.uprogress.directions_list.models.DirectionModel;
import com.vsokoltsov.uprogress.directions_list.models.DirectionsList;
import com.vsokoltsov.uprogress.directions_list.views.DirectionsListView;

import java.io.IOException;

import rx.Observable;
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
        directionsList()
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
        directionsList()
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
        view.startFooterLoader();
        directionsList()
                .subscribe(new Observer<DirectionsList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            view.stopFooterLoader();
                            view.failedResponse(e);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(DirectionsList list) {
                        view.stopFooterLoader();
                        view.successResponse(list);
                    }
                });
    }

    @Override
    public void createDirection(String userId, DirectionRequest directionRequest) {
        view.startLoader();
        model.createDirection(userId, directionRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DirectionResponse>() {
                    @Override
                    public void onCompleted() {
                        view.stopLoader();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.failedDirectionCreation(e);
                        view.stopLoader();
                    }

                    @Override
                    public void onNext(DirectionResponse response) {
                        view.successDirectionCreation(response.getDirection());
                    }
                });

    }

    @Override
    public void updateDirection(int userId, int directionId, DirectionRequest directionRequest) {
        view.startLoader();
        model.updateDirection(userId, directionId, directionRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DirectionResponse>() {
                    @Override
                    public void onCompleted() {
                        view.stopLoader();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.failedUpdateDirection(e);
                        view.stopLoader();
                    }

                    @Override
                    public void onNext(DirectionResponse response) {
                        view.successUpdateDirection(response.getDirection());
                    }
                });
    }

    @Override
    public void deleteDirection(int userId, int directionId) {

    }

    private Observable<DirectionsList> directionsList() {
        return model.getDirectionsList(user.getNick(), pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
