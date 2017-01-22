package com.example.vsokoltsov.uprogress.common.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.vsokoltsov.uprogress.common.BaseApplication;
import com.example.vsokoltsov.uprogress.common.utils.ContextManager;

/**
 * Created by vsokoltsov on 31.12.16.
 */

public class PreferencesHelper {

    private Context context;

    public static String TOKEN_PREF_NAME = "uprogresstoken";

    public PreferencesHelper(Context context) {
        this.context = context;
    }


    public void writeToken(String token) {
        SharedPreferences sPref = context.getSharedPreferences(BaseApplication.NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(TOKEN_PREF_NAME, token);
        ed.apply();
    }

    public String readToken() {
        SharedPreferences sPref = context.getSharedPreferences(BaseApplication.NAME, Context.MODE_PRIVATE);
        String str = sPref.getString(TOKEN_PREF_NAME, null);
        return sPref.getString(TOKEN_PREF_NAME, null);
    }

    public void deleteToken() {
        SharedPreferences.Editor editor = (SharedPreferences.Editor) context.getSharedPreferences(BaseApplication.NAME, Context.MODE_PRIVATE).edit();
        editor.putString(TOKEN_PREF_NAME, null);
        editor.commit();
    }

    public boolean isTokenExists() {
        String token = readToken();
        return token.length() > 0;
    }

}
