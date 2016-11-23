package com.example.vsokoltsov.uprogress.models.authorization;

import android.os.Build;

/**
 * Created by vsokoltsov on 23.11.16.
 */

public class Authorization {
    private String provider;
    private String platform;
    private String appName;
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
