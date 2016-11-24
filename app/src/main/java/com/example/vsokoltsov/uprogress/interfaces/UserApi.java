package com.example.vsokoltsov.uprogress.interfaces;

import com.example.vsokoltsov.uprogress.models.authorization.CurrentUser;
import com.example.vsokoltsov.uprogress.models.authorization.SignIn.SignInRequest;
import com.example.vsokoltsov.uprogress.models.authorization.Token;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by vsokoltsov on 23.11.16.
 */

public interface UserApi {
    @POST("sessions")
    Observable<Token> signIn(@Body SignInRequest user);

//    @POST("registrations")
//    Observable<Token> signUp(@Body SignUpRequest user);

    @GET("sessions/current")
    Observable<CurrentUser> getCurrentUser();
}
