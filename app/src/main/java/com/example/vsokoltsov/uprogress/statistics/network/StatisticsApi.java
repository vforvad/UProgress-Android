package com.example.vsokoltsov.uprogress.statistics.network;

import com.example.vsokoltsov.uprogress.direction_detail.model.steps.StepRequest;
import com.example.vsokoltsov.uprogress.direction_detail.model.steps.StepResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by vsokoltsov on 18.01.17.
 */

public interface StatisticsApi {
    @GET("statistics")
    Observable<StepResponse> createStep(
            @Path("user") String userNick,
            @Path("direction") String direction,
            @Body StepRequest request
    );
}
