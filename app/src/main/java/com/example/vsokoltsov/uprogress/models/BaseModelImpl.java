package com.example.vsokoltsov.uprogress.models;

import com.example.vsokoltsov.uprogress.utils.ApiRequester;

import retrofit2.Retrofit;

/**
 * Created by vsokoltsov on 31.12.16.
 */

public abstract class BaseModelImpl {
    protected Retrofit retrofit;

    public BaseModelImpl() {
        this.retrofit = ApiRequester.getInstance().getRestAdapter();
    }
}
