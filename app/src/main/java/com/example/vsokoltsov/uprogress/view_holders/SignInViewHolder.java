package com.example.vsokoltsov.uprogress.view_holders;

import android.widget.Button;
import android.widget.EditText;

/**
 * Created by vsokoltsov on 24.12.16.
 */

public class SignInViewHolder {
    public final EditText emailField;
    public final EditText passwordField;
    public final Button signInButton;

    public SignInViewHolder(EditText emailField, EditText passwordField, Button signInButton) {
        this.emailField = emailField;
        this.passwordField = passwordField;
        this.signInButton = signInButton;
    }
}
