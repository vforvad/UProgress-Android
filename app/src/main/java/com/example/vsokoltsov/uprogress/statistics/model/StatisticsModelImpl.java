package com.example.vsokoltsov.uprogress.statistics.model;

import android.content.Context;

import com.example.vsokoltsov.uprogress.common.BaseApplication;
import com.example.vsokoltsov.uprogress.common.utils.ApiRequester;
import com.example.vsokoltsov.uprogress.directions_list.network.DirectionsApi;
import com.example.vsokoltsov.uprogress.statistics.network.StatisticsApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by vsokoltsov on 18.01.17.
 */

public class StatisticsModelImpl implements StatisticsModel {
    StatisticsApi service;

    public StatisticsModelImpl(Context context) {
        Retrofit retrofit = ((BaseApplication) context.getApplicationContext()).getRetrofitClient();
        service = retrofit.create(StatisticsApi.class);
    }

    @Override
    public Observable<StatisticsResponse> getStatistics(String userID) {
        return service.getStatistics(userID);
    }
}
