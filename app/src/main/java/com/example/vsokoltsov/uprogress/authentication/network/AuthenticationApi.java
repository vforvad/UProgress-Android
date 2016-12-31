package com.example.vsokoltsov.uprogress.authentication.network;

import com.example.vsokoltsov.uprogress.authentication.models.CurrentUser;
import com.example.vsokoltsov.uprogress.authentication.models.SignIn.SignInRequest;
import com.example.vsokoltsov.uprogress.authentication.models.SignUp.SignUpRequest;
import com.example.vsokoltsov.uprogress.authentication.models.Token;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by vsokoltsov on 23.11.16.
 */

public interface AuthenticationApi {
    @POST("sessions")
    Observable<Token> signIn(@Body SignInRequest user);

    @POST("registrations")
    Observable<Token> signUp(@Body SignUpRequest user);

    @GET("sessions/current")
    Observable<CurrentUser> getCurrentUser();
}
