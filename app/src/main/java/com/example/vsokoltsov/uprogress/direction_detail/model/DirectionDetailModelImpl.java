package com.example.vsokoltsov.uprogress.direction_detail.model;

import com.example.vsokoltsov.uprogress.common.utils.ApiRequester;
import com.example.vsokoltsov.uprogress.direction_detail.model.steps.StepRequest;
import com.example.vsokoltsov.uprogress.direction_detail.model.steps.StepResponse;
import com.example.vsokoltsov.uprogress.direction_detail.network.StepsApi;
import com.example.vsokoltsov.uprogress.directions_list.models.Direction;
import com.example.vsokoltsov.uprogress.directions_list.network.DirectionsApi;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Observer;

/**
 * Created by vsokoltsov on 03.01.17.
 */

public class DirectionDetailModelImpl implements DirectionDetailModel {
    DirectionsApi directionsService;
    StepsApi stepService;

    public DirectionDetailModelImpl () {
        Retrofit retrofit = ApiRequester.getInstance().getRestAdapter();
        directionsService = retrofit.create(DirectionsApi.class);
        stepService = retrofit.create(StepsApi.class);
    }

    @Override
    public Observable<DirectionDetail> loadDirection(String userNick, String directionId) {
        return directionsService.getDirection(userNick, directionId);
    }

    @Override
    public Observable<StepResponse> updateStep(String userNick,
                                               String directionId, String stepId, StepRequest request) {
        return stepService.updateStep(userNick, directionId, stepId, request);
    }
}
