package com.example.vsokoltsov.uprogress.user.models;

import com.example.vsokoltsov.uprogress.common.utils.ApiRequester;
import com.example.vsokoltsov.uprogress.directions_list.network.DirectionsApi;
import com.example.vsokoltsov.uprogress.user.current.UserRequest;
import com.example.vsokoltsov.uprogress.user.current.UserResponse;
import com.example.vsokoltsov.uprogress.user.network.UserApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by vsokoltsov on 10.01.17.
 */

public class UserModelImpl implements UserModel {
    UserApi service;

    public UserModelImpl() {
        Retrofit retrofit = ApiRequester.getInstance().getRestAdapter();
        service = retrofit.create(UserApi.class);
    }


    @Override
    public Observable<UserResponse> updateUser(String userId, UserRequest request) {
        return service.updateUser(userId, request);
    }
}
