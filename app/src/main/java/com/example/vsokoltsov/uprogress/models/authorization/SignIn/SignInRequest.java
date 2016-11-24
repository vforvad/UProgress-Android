package com.example.vsokoltsov.uprogress.models.authorization.SignIn;

import com.example.vsokoltsov.uprogress.models.User;
import com.example.vsokoltsov.uprogress.models.authorization.Authorization;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by vsokoltsov on 23.11.16.
 */

public class SignInRequest {
    @JsonProperty("user")
    private SignInData data;

    public SignInRequest(String email, String password) {
        this.data = new SignInData(email, password);
    }

}
