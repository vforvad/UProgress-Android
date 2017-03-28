package com.vsokoltsov.uprogress.common;

import android.content.Context;
import android.content.Intent;

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

    }

    public void signUp(String action) {

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
