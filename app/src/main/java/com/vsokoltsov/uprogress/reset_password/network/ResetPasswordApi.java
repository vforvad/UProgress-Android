package com.vsokoltsov.uprogress.reset_password.network;

import com.vsokoltsov.uprogress.reset_password.model.ResetPasswordRequest;
import com.vsokoltsov.uprogress.reset_password.model.ResetPasswordResponse;
import com.vsokoltsov.uprogress.user.current.UserRequest;
import com.vsokoltsov.uprogress.user.current.UserResponse;

import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by vsokoltsov on 07.04.17.
 */

public interface ResetPasswordApi {
    @PUT("sessions/reset_password")
    Observable<ResetPasswordResponse> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);
}
