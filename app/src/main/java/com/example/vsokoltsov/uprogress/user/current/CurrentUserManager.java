package com.example.vsokoltsov.uprogress.user.current;

import android.content.Context;

import com.example.vsokoltsov.uprogress.authentication.network.AuthenticationApi;
import com.example.vsokoltsov.uprogress.common.BaseModelImpl;
import com.example.vsokoltsov.uprogress.user.current.network.CurrentUserApi;

import rx.Observable;

/**
 * Created by vsokoltsov on 31.12.16.
 */

public class CurrentUserManager extends BaseModelImpl implements CurrentUserModel {
    private CurrentUserApi service;

    public CurrentUserManager(Context context) {
        super(context);
        setService();

    }
    @Override
    public Observable<CurrentUser> getCurrentUser() {
        return service.getCurrentUser();
    }

    private void setService() {
        service = retrofit.create(CurrentUserApi.class);
    }
}
