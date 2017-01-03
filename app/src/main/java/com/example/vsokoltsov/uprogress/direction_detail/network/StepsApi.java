package com.example.vsokoltsov.uprogress.direction_detail.network;

import com.example.vsokoltsov.uprogress.direction_detail.model.DirectionDetail;
import com.example.vsokoltsov.uprogress.direction_detail.model.steps.StepRequest;
import com.example.vsokoltsov.uprogress.direction_detail.model.steps.StepResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by vsokoltsov on 03.01.17.
 */

public interface StepsApi {
    @PUT("users/{user}/directions/{direction}/steps/{step}")
    Observable<StepResponse> updateStep(
            @Path("user") String userNick,
            @Path("direction") String direction,
            @Path("step") String step,
            @Body StepRequest request
    );
}
