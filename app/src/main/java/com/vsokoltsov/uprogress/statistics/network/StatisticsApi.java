package com.vsokoltsov.uprogress.statistics.network;

import com.vsokoltsov.uprogress.direction_detail.model.steps.StepRequest;
import com.vsokoltsov.uprogress.direction_detail.model.steps.StepResponse;
import com.vsokoltsov.uprogress.statistics.model.StatisticsResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by vsokoltsov on 18.01.17.
 */

public interface StatisticsApi {
    @GET("users/{user}/statistics")
    Observable<StatisticsResponse> getStatistics(@Path("user") String userNick);
}
