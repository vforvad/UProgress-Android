package com.vsokoltsov.uprogress.common.utils;

/**
 * Created by vsokoltsov on 21.03.17.
 */

public class DeviceTokenManager {
    private static final DeviceTokenManager ourInstance = new DeviceTokenManager();
    private String token;
    private int appVersion;

    public static DeviceTokenManager getInstance() {
        return ourInstance;
    }

    private DeviceTokenManager() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
