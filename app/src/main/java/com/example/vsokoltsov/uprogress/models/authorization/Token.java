package com.example.vsokoltsov.uprogress.models.authorization;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.vsokoltsov.uprogress.BaseApplication;
import com.example.vsokoltsov.uprogress.utils.ApiRequester;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 23.11.16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Token {
    @JsonProperty("token")
    private String token;


    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
