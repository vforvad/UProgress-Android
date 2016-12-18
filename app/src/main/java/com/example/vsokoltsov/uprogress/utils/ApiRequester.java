package com.example.vsokoltsov.uprogress.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by vsokoltsov on 11.03.16.
 */
public class ApiRequester {
    private static final String APP_HOST = "http://154f5db6.ngrok.io";
    private static final String API_VERSION = "v1/";
    public static final String API_ADDRESS = APP_HOST + "/api/" + API_VERSION;
    public static String TOKEN_NAME = "uprogresstoken";
    public static String APP_NAME = "uprogress";
    public static Context context;

    public static class SingletonHolder {
        public static final ApiRequester HOLDER_INSTANCE = new ApiRequester();
    }

    public static ApiRequester getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    public String fullResourceURL(String url) {
        return APP_HOST + url;
    }


    private OkHttpClient okHttpClient() {
        OkHttpClient httpClientWLogging = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                String locale = Locale.getDefault().getLanguage();
                String token = getToken();
                Request.Builder request = chain.request().newBuilder()
                        .addHeader("locale", locale);
                if (token != null) {
                    request.addHeader(TOKEN_NAME, token);
                }
                return chain.proceed(request.build());
            }
        }).build();
        return httpClientWLogging;
    }

    public Retrofit getRestAdapter() {
        OkHttpClient client = this.okHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiRequester.API_ADDRESS)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit;

    }

    public void setToken(String token) {
        SharedPreferences sPref = this.context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(TOKEN_NAME, token);
        ed.apply();
    }

    public String getToken() {
        SharedPreferences sPref = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        String str = sPref.getString(TOKEN_NAME, null);
        return sPref.getString(TOKEN_NAME, null);
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
