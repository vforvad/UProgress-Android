package com.example.vsokoltsov.uprogress.common;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.authentication.messages.UserMessage;
import com.example.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.example.vsokoltsov.uprogress.user.current.CurrentUser;
import com.example.vsokoltsov.uprogress.navigation.NavigationDrawer;
import com.example.vsokoltsov.uprogress.common.utils.ContextManager;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by vsokoltsov on 22.11.16.
 */

public class ApplicationBaseActivity extends AppCompatActivity {
    private Toolbar mActionBarToolbar;
    private NavigationDrawer mNavigationDrawerFragment;
    private DrawerLayout drawerLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity_layout);
        setToolbar();
        setProgressBar();
        ContextManager.getInstance().setContext(this);
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

    public void setProgressBar() {
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }


    public void startProgressBar() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    public void stopProgressBar() {
        this.progressBar.setVisibility(View.INVISIBLE);
    }
}
