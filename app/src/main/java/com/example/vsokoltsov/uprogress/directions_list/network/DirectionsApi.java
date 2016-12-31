package com.example.vsokoltsov.uprogress.directions_list.network;

import com.example.vsokoltsov.uprogress.directions_list.models.DirectionsList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by vsokoltsov on 27.11.16.
 */

public interface DirectionsApi {
    @GET("users/{user}/directions")
    Observable<DirectionsList> getDirections(@Path("user") String userId, @Query("page") int page);
}
