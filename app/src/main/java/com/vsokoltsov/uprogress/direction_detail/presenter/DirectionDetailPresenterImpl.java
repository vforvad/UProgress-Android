package com.vsokoltsov.uprogress.direction_detail.presenter;

import com.vsokoltsov.uprogress.direction_detail.model.DirectionDetail;
import com.vsokoltsov.uprogress.direction_detail.model.DirectionDetailModel;
import com.vsokoltsov.uprogress.direction_detail.model.steps.StepRequest;
import com.vsokoltsov.uprogress.direction_detail.model.steps.StepResponse;
import com.vsokoltsov.uprogress.direction_detail.model.steps.StepsList;
import com.vsokoltsov.uprogress.direction_detail.view.DirectionDetailView;
import com.vsokoltsov.uprogress.directions_list.models.Direction;
import com.vsokoltsov.uprogress.directions_list.models.DirectionsList;

import java.io.IOException;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 03.01.17.
 */

public class DirectionDetailPresenterImpl implements DirectionDetailPresenter {
    DirectionDetailModel model;
    DirectionDetailView screen;

    public DirectionDetailPresenterImpl(DirectionDetailModel model, DirectionDetailView screen) {
        this.model = model;
        this.screen = screen;
    }

    @Override
    public void loadDirection(String userNick, String directionId) {
        screen.startLoader();

        getDirection(userNick, directionId)
                .subscribe(new Observer<DirectionDetail>() {
                    @Override
                    public void onCompleted() {
                        screen.stopLoader();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        screen.failureResponse(e);
                        screen.stopLoader();
                    }

                    @Override
                    public void onNext(DirectionDetail directionDetail) {
                        screen.successResponse(directionDetail.getDirection());
                    }
                });
    }

    @Override
    public void updateStep(String userNick, String directionId, String stepId, StepRequest request) {
        screen.startLoader();
        model.updateStep(userNick, directionId, stepId, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StepResponse>() {
                    @Override
                    public void onCompleted() {
                        screen.stopLoader();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        screen.failureStepUpdate(e);
                        screen.stopLoader();
                    }

                    @Override
                    public void onNext(StepResponse step) {
                        screen.successStepUpdate(step.getStep());
                    }
                });;
    }

    @Override
    public void reloadDirection(String userNick, String directionId) {
        screen.onStartRefresh();
        getDirection(userNick, directionId)
                .subscribe(new Observer<DirectionDetail>() {
                    @Override
                    public void onCompleted() {
                        screen.onStopRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        screen.failureResponse(e);
                        screen.onStopRefresh();
                    }

                    @Override
                    public void onNext(DirectionDetail directionDetail) {
                        screen.successResponse(directionDetail.getDirection());
                    }
                });
    }

    @Override
    public void deleteStep(String userNick, String directionId, String stepId, int[] positions) {
        screen.startLoader();
        model.deleteStep(userNick, directionId, stepId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StepResponse>() {
                    @Override
                    public void onCompleted() {
                        screen.stopLoader();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        screen.failedDelete(e);
                        screen.stopLoader();
                    }

                    @Override
                    public void onNext(StepResponse stepResponse) {
                        screen.successDelete(stepResponse.getStep(), positions);
                    }
                });

    }

    @Override
    public void createStep(String userNick, String directionId, StepRequest request) {
        screen.startLoader();
        model.createStep(userNick, directionId, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StepResponse>() {
                    @Override
                    public void onCompleted() {
                        screen.stopLoader();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        screen.failedStepCreate(e);
                        screen.stopLoader();
                    }

                    @Override
                    public void onNext(StepResponse stepResponse) {
                        screen.successStepCreate(stepResponse.getStep());
                    }
                });
    }

    private Observable<DirectionDetail> getDirection(String userNick, String directionId) {
        return model.loadDirection(userNick, directionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
