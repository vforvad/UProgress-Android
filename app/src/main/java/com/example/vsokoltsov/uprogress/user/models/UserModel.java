package com.example.vsokoltsov.uprogress.user.models;

import com.example.vsokoltsov.uprogress.user.current.UserRequest;
import com.example.vsokoltsov.uprogress.user.current.UserResponse;

import rx.Observable;

/**
 * Created by vsokoltsov on 10.01.17.
 */

public interface UserModel {
    Observable<UserResponse> updateUser(
            String userId,
            UserRequest request
    );
}
