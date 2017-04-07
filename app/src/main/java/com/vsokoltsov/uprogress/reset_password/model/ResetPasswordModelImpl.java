package com.vsokoltsov.uprogress.reset_password.model;

import android.content.Context;

import com.vsokoltsov.uprogress.common.BaseApplication;
import com.vsokoltsov.uprogress.direction_detail.network.StepsApi;
import com.vsokoltsov.uprogress.directions_list.network.DirectionsApi;
import com.vsokoltsov.uprogress.reset_password.network.ResetPasswordApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by vsokoltsov on 07.04.17.
 */

public class ResetPasswordModelImpl implements ResetPasswordModel {
    ResetPasswordApi resetPasswordApi;

    public ResetPasswordModelImpl (Context context) {
        Retrofit retrofit = ((BaseApplication) context.getApplicationContext()).getRetrofitClient();
        resetPasswordApi = retrofit.create(ResetPasswordApi.class);
    }
    @Override
    public Observable<ResetPasswordResponse> resetPassword(ResetPasswordRequest request) {
        return resetPasswordApi.resetPassword(request);
    }
}
