package com.example.vsokoltsov.uprogress.common;

import android.app.Application;
import android.content.Context;

import com.example.vsokoltsov.uprogress.common.helpers.ImageHelper;
import com.example.vsokoltsov.uprogress.common.helpers.PreferencesHelper;
import com.example.vsokoltsov.uprogress.common.utils.ApiRequester;

import retrofit2.Retrofit;

/**
 * Created by vsokoltsov on 25.12.16.
 */

public class BaseApplication extends Application {
    private Context context;
    private ImageHelper imageHelper;
    private Retrofit retrofitClient;
    private PreferencesHelper preferencesHelper;

    public static String NAME = "uprogress";

    @Override
    public void onCreate() {
        super.onCreate();
        imageHelper = new ImageHelper(getApplicationContext());
        retrofitClient = ApiRequester.getInstance().getRestAdapter(getApplicationContext());
        preferencesHelper = new PreferencesHelper(getApplicationContext());
    }


    public Retrofit getRetrofitClient() {
        return retrofitClient;
    }

    public ImageHelper getImageHelper() {
        return imageHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return preferencesHelper;
    }
}
