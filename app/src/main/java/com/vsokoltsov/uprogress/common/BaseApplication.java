package com.vsokoltsov.uprogress.common;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;

import com.vsokoltsov.uprogress.common.helpers.ImageHelper;
import com.vsokoltsov.uprogress.common.helpers.PreferencesHelper;
import com.vsokoltsov.uprogress.common.utils.ApiRequester;

import retrofit2.Retrofit;

/**
 * Created by vsokoltsov on 25.12.16.
 */

public class BaseApplication extends Application {
    private ImageHelper imageHelper;
    protected Retrofit retrofitClient;
    private PreferencesHelper preferencesHelper;
    private PackageInfo packageInfo;

    public static String NAME = "uprogress";

    @Override
    public void onCreate() {
        super.onCreate();
        imageHelper = new ImageHelper(getApplicationContext());
        setRetrofitClient(getApplicationContext());
        preferencesHelper = new PreferencesHelper(getApplicationContext());
        setPackageInfo();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void setRetrofitClient(Context context) {
        retrofitClient = ApiRequester.getInstance().getRestAdapter(context);
    }

    public void setPackageInfo() {
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
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

    public void setApiUrl(Context context, String url) {
        ApiRequester.API_ADDRESS = url + "/";
        retrofitClient = ApiRequester.getInstance().getRestAdapter(context);
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }
}
