package com.vsokoltsov.uprogress.direction_detail.model;

import android.content.Context;

import com.vsokoltsov.uprogress.common.BaseApplication;
import com.vsokoltsov.uprogress.common.utils.ApiRequester;
import com.vsokoltsov.uprogress.direction_detail.model.steps.StepRequest;
import com.vsokoltsov.uprogress.direction_detail.model.steps.StepResponse;
import com.vsokoltsov.uprogress.direction_detail.model.steps.StepsList;
import com.vsokoltsov.uprogress.direction_detail.network.StepsApi;
import com.vsokoltsov.uprogress.directions_list.models.Direction;
import com.vsokoltsov.uprogress.directions_list.network.DirectionsApi;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Observer;

/**
 * Created by vsokoltsov on 03.01.17.
 */

public class DirectionDetailModelImpl implements DirectionDetailModel {
    DirectionsApi directionsService;
    StepsApi stepService;

    public DirectionDetailModelImpl(Context context) {
        Retrofit retrofit = ((BaseApplication) context.getApplicationContext()).getRetrofitClient();
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

    @Override
    public Observable<StepsList> loadSteps(String userNick, String directionId, int pageNumber) {
        return stepService.getSteps(userNick, directionId, pageNumber);
    }

    @Override
    public Observable<StepResponse> deleteStep(String userNick, String directionId, String stepId) {
        return stepService.deleteSteps(userNick, directionId, stepId);
    }

    @Override
    public Observable<StepResponse> createStep(String userNick, String directionId, StepRequest request) {
        return stepService.createStep(userNick, directionId, request);
    }
}
