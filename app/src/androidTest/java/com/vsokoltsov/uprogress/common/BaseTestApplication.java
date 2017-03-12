package com.vsokoltsov.uprogress.common;

import android.content.Context;

import com.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.vsokoltsov.uprogress.common.BaseApplication;
import com.vsokoltsov.uprogress.common.utils.ApiRequester;
import com.vsokoltsov.uprogress.user.current.User;

import java.io.IOException;
import java.net.InetAddress;

import okhttp3.mockwebserver.MockWebServer;

/**
 * Created by vsokoltsov on 09.03.17.
 */

public class BaseTestApplication extends BaseApplication {
    private MockWebServer server;

    @Override
    public void onCreate() {
        User user = new User();
        user.setNick("vforvad");
        user.setId(1000);
        AuthorizationService.getInstance().setCurrentUser(user);
        super.onCreate();
    }

    @Override
    public void setRetrofitClient(Context context) {
        server = new MockWebServer();
        ApiRequester.API_ADDRESS = "http://localhost:3000/";
        super.setRetrofitClient(context);
    }

    public MockWebServer getServer() {
        return server;
    }
}
