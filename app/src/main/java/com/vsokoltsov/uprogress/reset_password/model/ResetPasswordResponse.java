package com.vsokoltsov.uprogress.reset_password.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 07.04.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResetPasswordResponse {
    @JsonProperty("message")
    private String message;

    public ResetPasswordResponse() {  }

    public ResetPasswordResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
