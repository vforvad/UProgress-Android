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

    public static String NAME = "uprogresstoken";
    private static Context context = BaseApplication.getAppContext();

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static void writeToken(String token) {
        SharedPreferences sPref = context.getSharedPreferences(BaseApplication.NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(NAME, token);
        ed.apply();
    }

    public static String readToken() {
        SharedPreferences sPref = context.getSharedPreferences(BaseApplication.NAME, Context.MODE_PRIVATE);
        String str = sPref.getString(NAME, null);
        return sPref.getString(NAME, null);
    }

    public static void deleteToken() {
        SharedPreferences.Editor editor = (SharedPreferences.Editor) context.getSharedPreferences(BaseApplication.NAME, Context.MODE_PRIVATE).edit();
        editor.putString(NAME, null);
        editor.commit();
    }
}
