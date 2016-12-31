package com.example.vsokoltsov.uprogress.user.current.network;

import com.example.vsokoltsov.uprogress.user.current.CurrentUser;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by vsokoltsov on 31.12.16.
 */

public interface CurrentUserApi {
    @GET("sessions/current")
    Observable<CurrentUser> getCurrentUser();
}
