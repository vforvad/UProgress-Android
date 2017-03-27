package com.vsokoltsov.uprogress.authentication.models.SignUp;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vsokoltsov.uprogress.authentication.models.SignIn.SignInData;

/**
 * Created by vsokoltsov on 24.11.16.
 */

public class SignUpRequest {
    @JsonProperty("user")
    private SignUpData data;

    public SignUpRequest(String email, String password, String passwordConfirmation, String nick) {
        this.data = new SignUpData(email, password, passwordConfirmation, nick);
    }

    public SignUpRequest(String email, String password, String passwordConfirmation, String nick, Context context) {
        this.data = new SignUpData(email, password, passwordConfirmation, nick, context);
    }
}
