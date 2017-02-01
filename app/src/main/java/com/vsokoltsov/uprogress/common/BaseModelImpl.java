package com.vsokoltsov.uprogress.common;

import android.content.Context;

import com.vsokoltsov.uprogress.common.utils.ApiRequester;

import retrofit2.Retrofit;

/**
 * Created by vsokoltsov on 31.12.16.
 */

public abstract class BaseModelImpl {
    protected Retrofit retrofit;

    public BaseModelImpl(Context context) {
        this.retrofit = ((BaseApplication) context.getApplicationContext()).getRetrofitClient();
    }
}
