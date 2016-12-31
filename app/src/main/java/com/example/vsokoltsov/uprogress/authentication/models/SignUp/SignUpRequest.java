package com.example.vsokoltsov.uprogress.authentication.models.SignUp;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 24.11.16.
 */

public class SignUpRequest {
    @JsonProperty("user")
    private SignUpData data;

    public SignUpRequest(String email, String password, String passwordConfirmation, String nick) {
        this.data = new SignUpData(email, password, passwordConfirmation, nick);
    }
}
