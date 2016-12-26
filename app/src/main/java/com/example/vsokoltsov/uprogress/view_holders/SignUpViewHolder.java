package com.example.vsokoltsov.uprogress.view_holders;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Button;
import android.widget.EditText;

import com.example.vsokoltsov.uprogress.R;

/**
 * Created by vsokoltsov on 26.12.16.
 */

public class SignUpViewHolder {
    public final EditText emailField;
    public final EditText passwordField;
    public final EditText passwordConfirmationField;
    public final EditText nickField;
    private final Button signUpButton;

    static
    {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public SignUpViewHolder(EditText emailField, EditText passwordField,
                            EditText passwordConfirmationField, EditText nickField, Button signUpButton) {
        this.emailField = emailField;
        this.passwordField = passwordField;
        this.passwordConfirmationField = passwordConfirmationField;
        this.nickField = nickField;
        this.signUpButton = signUpButton;
    }

    public void setFields(Context context) {
        Drawable emailImg = ContextCompat.getDrawable(context, R.drawable.email);
        Drawable passwordImg = ContextCompat.getDrawable(context, R.drawable.password);
        Drawable nickImg = ContextCompat.getDrawable(context, R.drawable.user_nick);

        emailField.setCompoundDrawablesWithIntrinsicBounds( emailImg, null, null, null);
        passwordField.setCompoundDrawablesWithIntrinsicBounds( passwordImg, null, null, null);
        passwordConfirmationField.setCompoundDrawablesWithIntrinsicBounds( passwordImg, null, null, null);
        nickField.setCompoundDrawablesWithIntrinsicBounds( nickImg, null, null, null);
    }
}
