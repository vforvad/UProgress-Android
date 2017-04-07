package com.vsokoltsov.uprogress.authentication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.vsokoltsov.uprogress.directions_list.ui.DirectionsActivity;
import com.vsokoltsov.uprogress.user.ui.UserActivity;

public class AuthorizationActivity extends ApplicationBaseActivity{
    private String action;
    private android.support.v4.app.FragmentManager fragmentManager;
    public AuthorizationBaseFragment authorizationBaseFragment;
    private CountingIdlingResource mIdlingResource;
    private String resetPasswordMessage;

    static
    {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setLeftNavigationBar();
        defineCurrentTab();
        setFragment();
    }

    private void setFragment() {
        Bundle arguments = new Bundle();
        if (action == null) {
            action = "sign_in";
        }
        arguments.putString("action", action);

        if (resetPasswordMessage != null) {
            super.setLeftNavigationBar();
            arguments.putString("reset_password_message", resetPasswordMessage);
        }
        fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        authorizationBaseFragment = new AuthorizationBaseFragment();
        authorizationBaseFragment.setArguments(arguments);
        fragmentTransaction.replace(R.id.main_content, authorizationBaseFragment);
        fragmentTransaction.commit();
    }

    private void defineCurrentTab() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            action = (String) extras.getString("action");
            resetPasswordMessage = (String) extras.getString("reset_password_message");
        }
    }

    public void redirectToProfile() {
        Intent userActivity = new Intent(this, UserActivity.class);
        startActivity(userActivity);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    @VisibleForTesting
    public CountingIdlingResource getIdlingResourceInit()
    {
        if(mIdlingResource == null)
            mIdlingResource = new CountingIdlingResource(getClass().getName(), true);

        return mIdlingResource;
    }

    @VisibleForTesting
    @Nullable
    public CountingIdlingResource getIdlingResource()
    {
        return mIdlingResource;
    }
}
