package com.vsokoltsov.uprogress.direction_detail.network;

import com.vsokoltsov.uprogress.direction_detail.model.DirectionDetail;
import com.vsokoltsov.uprogress.direction_detail.model.steps.StepRequest;
import com.vsokoltsov.uprogress.direction_detail.model.steps.StepResponse;
import com.vsokoltsov.uprogress.direction_detail.model.steps.StepsList;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by vsokoltsov on 03.01.17.
 */

public interface StepsApi {
    @POST("users/{user}/directions/{direction}/steps")
    Observable<StepResponse> createStep(
            @Path("user") String userNick,
            @Path("direction") String direction,
            @Body StepRequest request
    );

    @PUT("users/{user}/directions/{direction}/steps/{step}")
    Observable<StepResponse> updateStep(
            @Path("user") String userNick,
            @Path("direction") String direction,
            @Path("step") String step,
            @Body StepRequest request
    );
    @GET("users/{user}/directions/{direction}/steps")
    Observable<StepsList> getSteps(
            @Path("user") String userNick,
            @Path("direction") String direction,
            @Query("page") int page
    );
    @DELETE("users/{user}/directions/{direction}/steps/{step}")
    Observable<StepResponse> deleteSteps(
            @Path("user") String userNick,
            @Path("direction") String direction,
            @Path("step") String stepId
    );
}
