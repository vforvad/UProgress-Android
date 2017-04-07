package com.vsokoltsov.uprogress.reset_password.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vsokoltsov.uprogress.authentication.models.RestorePassword.RestorePasswordData;

/**
 * Created by vsokoltsov on 07.04.17.
 */

public class ResetPasswordRequest {
    @JsonProperty("user")
    private final ResetPasswordData restorePasswordData;

    public ResetPasswordRequest(String password, String passwordConfirmation, String token) {
        this.restorePasswordData = new ResetPasswordData(password, passwordConfirmation, token);
    }
}
