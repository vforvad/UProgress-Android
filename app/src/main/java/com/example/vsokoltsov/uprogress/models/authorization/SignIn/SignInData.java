package com.example.vsokoltsov.uprogress.models.authorization.SignIn;

import com.example.vsokoltsov.uprogress.models.authorization.Authorization;

/**
 * Created by vsokoltsov on 24.11.16.
 */

public class SignInData {
    private String email;
    private String password;
    private Authorization authorization = new Authorization();

    public SignInData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
