package com.example.vsokoltsov.uprogress.direction_detail.presenter;

import com.example.vsokoltsov.uprogress.direction_detail.model.DirectionDetail;
import com.example.vsokoltsov.uprogress.direction_detail.model.DirectionDetailModel;
import com.example.vsokoltsov.uprogress.direction_detail.view.DirectionDetailView;
import com.example.vsokoltsov.uprogress.directions_list.models.Direction;
import com.example.vsokoltsov.uprogress.directions_list.models.DirectionsList;

import java.io.IOException;

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
        model.loadDirection(userNick, directionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
}
