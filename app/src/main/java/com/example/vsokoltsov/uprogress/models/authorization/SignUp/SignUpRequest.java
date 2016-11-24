package com.example.vsokoltsov.uprogress.models.authorization.SignUp;

import com.example.vsokoltsov.uprogress.models.authorization.SignIn.SignInData;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 24.11.16.
 */

public class SignUpRequest {
    @JsonProperty("user")
    private SignInData data;

    public SignUpRequest(String email, String password, String passwordConfirmation, String nick) {
        this.data = new SignUpData(email, password, passwordConfirmation, nick);
    }
}
