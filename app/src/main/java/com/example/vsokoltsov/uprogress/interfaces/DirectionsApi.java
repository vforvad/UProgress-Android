package com.example.vsokoltsov.uprogress.interfaces;

import com.example.vsokoltsov.uprogress.models.authorization.CurrentUser;
import com.example.vsokoltsov.uprogress.models.directions.DirectionsList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by vsokoltsov on 27.11.16.
 */

public interface DirectionsApi {
    @GET("users/{user}/directions")
    Observable<DirectionsList> getDirections(@Path("user") String userId );
}
