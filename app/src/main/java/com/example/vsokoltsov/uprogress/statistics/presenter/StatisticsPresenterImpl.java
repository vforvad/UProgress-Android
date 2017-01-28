package com.example.vsokoltsov.uprogress.statistics.presenter;

import com.example.vsokoltsov.uprogress.directions_list.models.DirectionResponse;
import com.example.vsokoltsov.uprogress.statistics.model.StatisticsModel;
import com.example.vsokoltsov.uprogress.statistics.model.StatisticsResponse;
import com.example.vsokoltsov.uprogress.statistics.views.StatisticsView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 18.01.17.
 */

public class StatisticsPresenterImpl implements StatisticsPresenter {
    private final StatisticsModel model;
    private final StatisticsView view;

    public StatisticsPresenterImpl(StatisticsModel model, StatisticsView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getStatistics(String userId) {
        view.startLoader();
        model.getStatistics(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StatisticsResponse>() {
                    @Override
                    public void onCompleted() {
                        view.stopLoader();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.failedLoadStatistics(e);
                        view.stopLoader();
                    }

                    @Override
                    public void onNext(StatisticsResponse response) {
                        view.successLoadStatistics(response.getStatistics());
                    }
                });
    }
}
