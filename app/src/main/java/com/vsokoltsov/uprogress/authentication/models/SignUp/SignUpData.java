package com.vsokoltsov.uprogress.authentication.models.SignUp;


import android.content.Context;

import com.vsokoltsov.uprogress.authentication.models.Authorization;
import com.vsokoltsov.uprogress.authentication.models.SignIn.SignInData;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 24.11.16.
 */

public class SignUpData extends SignInData {
    @JsonProperty("password_confirmation")
    private String passwordConfirmation;
    private String nick;

    public SignUpData(String email, String password, String passwordConfirmation, String nick) {
        super(email, password);
        this.passwordConfirmation = passwordConfirmation;
        this.nick = nick;
    }

    public SignUpData(String email, String password,
                      String passwordConfirmation, String nick, Context context) {
        super(email, password);
        this.passwordConfirmation = passwordConfirmation;
        this.nick = nick;
        this.authorization = new Authorization(context);
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }
}
