package com.example.vsokoltsov.uprogress.models.authorization.SignUp;


import com.example.vsokoltsov.uprogress.models.authorization.SignIn.SignInData;
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
