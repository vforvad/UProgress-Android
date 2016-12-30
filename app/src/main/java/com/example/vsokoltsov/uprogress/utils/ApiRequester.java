package com.example.vsokoltsov.uprogress.utils;
import android.content.Context;

import com.example.vsokoltsov.uprogress.helpers.PreferencesHelper;
import com.example.vsokoltsov.uprogress.models.authorization.Token;

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

    public static class SingletonHolder {
        public static final ApiRequester HOLDER_INSTANCE = new ApiRequester();
    }

    public static ApiRequester getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    public String fullResourceURL(String url) {
        return APP_HOST + url;
    }


    private OkHttpClient okHttpClient(PreferencesHelper preferencesHelper) {
        return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                String locale = Locale.getDefault().getLanguage();
                String token = preferencesHelper.readToken();
                Request.Builder request = chain.request().newBuilder();
                if (token != null) {
                    request.addHeader(PreferencesHelper.TOKEN_PREF_NAME, token);
                }
                return chain.proceed(request.build());
            }
        }).build();
    }

    public Retrofit getRestAdapter(PreferencesHelper preferencesHelper) {
        OkHttpClient client = this.okHttpClient(preferencesHelper);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiRequester.API_ADDRESS)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit;

    }
}
