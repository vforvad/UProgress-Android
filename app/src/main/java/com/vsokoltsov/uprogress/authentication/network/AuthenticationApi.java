package com.vsokoltsov.uprogress.authentication.network;

import com.vsokoltsov.uprogress.user.current.CurrentUser;
import com.vsokoltsov.uprogress.authentication.models.SignIn.SignInRequest;
import com.vsokoltsov.uprogress.authentication.models.SignUp.SignUpRequest;
import com.vsokoltsov.uprogress.authentication.models.Token;
import com.vsokoltsov.uprogress.user.current.network.CurrentUserApi;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by vsokoltsov on 23.11.16.
 */

public interface AuthenticationApi{
    @POST("sessions")
    Observable<Token> signIn(@Body SignInRequest user);

    @POST("registrations")
    Observable<Token> signUp(@Body SignUpRequest user);
}
