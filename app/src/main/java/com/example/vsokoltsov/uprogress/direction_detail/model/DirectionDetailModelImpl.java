package com.example.vsokoltsov.uprogress.direction_detail.model;

import com.example.vsokoltsov.uprogress.common.utils.ApiRequester;
import com.example.vsokoltsov.uprogress.directions_list.models.Direction;
import com.example.vsokoltsov.uprogress.directions_list.network.DirectionsApi;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Observer;

/**
 * Created by vsokoltsov on 03.01.17.
 */

public class DirectionDetailModelImpl implements DirectionDetailModel {
    DirectionsApi service;

    public DirectionDetailModelImpl () {
        Retrofit retrofit = ApiRequester.getInstance().getRestAdapter();
        service = retrofit.create(DirectionsApi.class);
    }

    @Override
    public Observable<DirectionDetail> loadDirection(String userNick, String directionId) {
        return service.getDirection(userNick, directionId);
    }
}
