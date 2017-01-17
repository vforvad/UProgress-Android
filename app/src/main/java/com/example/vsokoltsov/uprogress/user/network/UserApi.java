package com.example.vsokoltsov.uprogress.user.network;


import com.example.vsokoltsov.uprogress.user.current.UserRequest;
import com.example.vsokoltsov.uprogress.user.current.UserResponse;

import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by vsokoltsov on 10.01.17.
 */

public interface UserApi {
    @PUT("users/{user}")
    Observable<UserResponse> updateUser(@Path("user") String userNick, @Body UserRequest userRequest);
}
