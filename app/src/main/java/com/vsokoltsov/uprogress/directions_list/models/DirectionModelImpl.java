package com.vsokoltsov.uprogress.directions_list.models;

import android.content.Context;

import com.vsokoltsov.uprogress.common.BaseApplication;
import com.vsokoltsov.uprogress.directions_list.network.DirectionsApi;
import com.vsokoltsov.uprogress.common.utils.ApiRequester;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by vsokoltsov on 29.12.16.
 */

public class DirectionModelImpl implements DirectionModel {
    DirectionsApi service;

    public DirectionModelImpl(Context context) {
        Retrofit retrofit = ((BaseApplication) context.getApplicationContext()).getRetrofitClient();
        service = retrofit.create(DirectionsApi.class);
    }

    @Override
    public Observable<DirectionsList> getDirectionsList(String userId, int pageNumber) {
        return service.getDirections(userId, pageNumber);
    }

    @Override
    public Observable<DirectionResponse> createDirection(String userId, DirectionRequest directionRequest) {
        return service.createDirection(userId, directionRequest);
    }

    @Override
    public Observable<DirectionResponse> updateDirection(int userId, int directionId, DirectionRequest request) {
        return service.updateDirection(userId, directionId, request);
    }

    @Override
    public Observable<DirectionResponse> deleteDirection(int userId, int directionId) {
        return service.deleteDirection(userId, directionId);
    }
}
