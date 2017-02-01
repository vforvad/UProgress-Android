package com.vsokoltsov.uprogress.directions_list.network;

import com.vsokoltsov.uprogress.direction_detail.model.DirectionDetail;
import com.vsokoltsov.uprogress.directions_list.models.Direction;
import com.vsokoltsov.uprogress.directions_list.models.DirectionRequest;
import com.vsokoltsov.uprogress.directions_list.models.DirectionResponse;
import com.vsokoltsov.uprogress.directions_list.models.DirectionsList;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by vsokoltsov on 27.11.16.
 */

public interface DirectionsApi {
    @GET("users/{user}/directions")
    Observable<DirectionsList> getDirections(@Path("user") String userId, @Query("page") int page);
    @GET("users/{user}/directions/{direction}")
    Observable<DirectionDetail> getDirection(@Path("user") String userNick, @Path("direction") String direction);
    @POST("users/{user}/directions")
    Observable<DirectionResponse> createDirection(@Path("user") String userNick, @Body DirectionRequest directionRequest);
    @PUT("users/{user}/directions/{direction}")
    Observable<DirectionResponse> updateDirection(@Path("user") int userId, @Path("direction") int directionId, @Body DirectionRequest directionRequest);
    @DELETE("users/{user}/directions/{direction}")
    Observable<DirectionResponse> deleteDirection(@Path("user") int userId, @Path("direction") int directionId);
}
