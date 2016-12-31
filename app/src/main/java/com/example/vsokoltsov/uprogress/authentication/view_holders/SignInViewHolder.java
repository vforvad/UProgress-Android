package com.example.vsokoltsov.uprogress.authentication.view_holders;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Button;
import android.widget.EditText;

import com.example.vsokoltsov.uprogress.R;

/**
 * Created by vsokoltsov on 24.12.16.
 */

public class SignInViewHolder {
    public final EditText emailField;
    public final EditText passwordField;
    private final Button signInButton;

    static
    {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public SignInViewHolder(EditText emailField, EditText passwordField, Button signInButton) {
        this.emailField = emailField;
        this.passwordField = passwordField;
        this.signInButton = signInButton;
    }

    public void setFields(Context context) {
        Drawable emailImg = ContextCompat.getDrawable(context, R.drawable.email);
        Drawable passwordImg = ContextCompat.getDrawable(context, R.drawable.password);

        emailField.setCompoundDrawablesWithIntrinsicBounds( emailImg, null, null, null);
        passwordField.setCompoundDrawablesWithIntrinsicBounds( passwordImg, null, null, null);
    }
}
