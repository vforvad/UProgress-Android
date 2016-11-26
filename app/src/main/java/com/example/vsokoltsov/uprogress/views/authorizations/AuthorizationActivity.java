package com.example.vsokoltsov.uprogress.views.authorizations;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.views.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.views.navigation.NavigationDrawer;

public class AuthorizationActivity extends ApplicationBaseActivity{
    private String action;
    private android.support.v4.app.FragmentManager fragmentManager;
    public AuthorizationBaseFragment authorizationBaseFragment;
    private Toolbar mActionBarToolbar;
    private NavigationDrawer mNavigationDrawerFragment;
    private DrawerLayout drawerLayout;

    static
    {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization_activity);
        super.setToolbar();
        super.setLeftNavigationBar();
        defineCurrentTab();
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

    public void setToolbar() {
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);
    }

    public void setLeftNavigationBar() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawerFragment = (NavigationDrawer) fragmentManager.findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, drawerLayout);
    }

    private void defineCurrentTab() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            action = (String) extras.getString("action");
        }
    }
}
