package com.vsokoltsov.uprogress.reset_password.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 07.04.17.
 */

public class ResetPasswordData {
    @JsonProperty("password")
    private final String password;
    @JsonProperty("password_confirmation")
    private final String passwordConfirmation;
    @JsonProperty("token")
    private final String token;

    public ResetPasswordData(String password, String passwordConfirmation, String token) {
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.token = token;
    }
}
