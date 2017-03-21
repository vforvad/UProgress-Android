package com.vsokoltsov.uprogress.authentication.models.SignIn;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 23.11.16.
 */

public class SignInRequest {
    @JsonProperty("user")
    private SignInData data;

    public SignInRequest(String email, String password) {
        this.data = new SignInData(email, password);
    }

    public SignInRequest(String email, String password, Context context) {
        this.data = new SignInData(email, password, context);
    }
}
