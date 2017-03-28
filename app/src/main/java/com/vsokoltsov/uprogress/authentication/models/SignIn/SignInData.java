package com.vsokoltsov.uprogress.authentication.models.SignIn;

import android.content.Context;

import com.vsokoltsov.uprogress.authentication.models.Authorization;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 24.11.16.
 */

public class SignInData {
    private String email;
    private String password;
    protected Authorization authorization;
    @JsonProperty("device_token")
    private String deviceToken;

    public SignInData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public SignInData(String email, String password, Context context) {
        this.email = email;
        this.password = password;
        this.authorization = new Authorization(context);
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
