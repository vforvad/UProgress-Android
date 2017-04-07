package com.vsokoltsov.uprogress.authentication.models.RestorePassword;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 07.04.17.
 */

public class RestorePasswordRequest {
    @JsonProperty("user")
    private RestorePasswordData restorePasswordData;

    public RestorePasswordRequest(String email) {
        this.restorePasswordData = new RestorePasswordData(email);
    }
}
