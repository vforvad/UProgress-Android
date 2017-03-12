package com.vsokoltsov.uprogress.authentication.models;

import android.os.Build;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 23.11.16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Authorization {
    private String provider;
    private String platform;
    @JsonProperty("app_name")
    private String appName;
    @JsonProperty("app_version")
    private String appVersion;

    public String getProvider() {
        return "UProgress";
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPlatform() {
        return android.os.Build.MODEL;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppName() {
        return "Android " + Build.VERSION.CODENAME;
    }

    public String getAppVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}
