package com.example.vsokoltsov.uprogress.authentication.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;

public class AuthorizationActivity extends ApplicationBaseActivity{
    private String action;
    private android.support.v4.app.FragmentManager fragmentManager;
    public AuthorizationBaseFragment authorizationBaseFragment;

    static
    {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defineCurrentTab();
        setFragment();
    }

    private void setFragment() {
        Bundle arguments = new Bundle();
        if (action == null) {
            action = "sign_in";
        }
        arguments.putString("action", action);
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
        }
    }
}