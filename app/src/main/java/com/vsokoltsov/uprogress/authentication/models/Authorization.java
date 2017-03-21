package com.vsokoltsov.uprogress.authentication.models;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vsokoltsov.uprogress.common.BaseApplication;
import com.vsokoltsov.uprogress.common.utils.DeviceTokenManager;

/**
 * Created by vsokoltsov on 23.11.16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Authorization {
    private PackageInfo packageInfo;
    private static final String PLATFORM = "Android";
    private static final String APP_NAME = "UProgress";

    @JsonProperty("provider")
    private String provider;
    @JsonProperty("platform")
    private String platform;
    @JsonProperty("app_name")
    private String appName;
    @JsonProperty("app_version")
    private String appVersion;
    @JsonProperty("device_token")
    private String token;

    public Authorization() {}

    public Authorization(Context context) {
        packageInfo = ((BaseApplication) context.getApplicationContext()).getPackageInfo();
    }

    public String getProvider() {
        return APP_NAME;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPlatform() {
        return PLATFORM;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppName() {
        return APP_NAME;
    }

    public String getAppVersion() {
        return packageInfo.versionName;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getToken() {
        return DeviceTokenManager.getInstance().getToken();
    }
}
