package com.vsokoltsov.uprogress.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.authentication.ui.AuthorizationActivity;
import com.vsokoltsov.uprogress.directions_list.ui.DirectionsActivity;
import com.vsokoltsov.uprogress.user.current.User;

/**
 * Created by vsokoltsov on 17.03.17.
 */

public class NavigationFragments {
    private Context context;

    public NavigationFragments(Context context) {
        this.context = context;
    }

    public void signIn(String action) {
        Intent signInActivity = new Intent(context, AuthorizationActivity.class);
        signInActivity.putExtra("action", action);
        context.startActivity(signInActivity);
        ((Activity) context).overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void signUp(String action) {
        Intent signInActivity = new Intent(context, AuthorizationActivity.class);
        signInActivity.putExtra("action", action);
        context.startActivity(signInActivity);
        ((Activity) context).overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void signIn(String action, String message) {
        Intent signInActivity = new Intent(context, AuthorizationActivity.class);
        signInActivity.putExtra("action", action);
        signInActivity.putExtra("reset_password_message", message);
        context.startActivity(signInActivity);
        ((Activity) context).overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void directions() {
        Intent dirActivity = new Intent(context, DirectionsActivity.class);
        context.startActivity(dirActivity);
    }

    public void statistics() {

    }

    public void userProfile(User user) {

    }
}
