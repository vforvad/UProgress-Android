package com.vsokoltsov.uprogress.authentication.models.RestorePassword;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 07.04.17.
 */

public class RestorePasswordData {
    @JsonProperty("email")
    private String email;

    public RestorePasswordData(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
