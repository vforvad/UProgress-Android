package com.example.vsokoltsov.uprogress.common;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.example.vsokoltsov.uprogress.navigation.NavigationPresenter;
import com.example.vsokoltsov.uprogress.common.utils.ContextManager;
import com.example.vsokoltsov.uprogress.user.current.User;

/**
 * Created by vsokoltsov on 22.11.16.
 */

public class ApplicationBaseActivity extends AppCompatActivity {
    private Toolbar mActionBarToolbar;
    private ProgressBar progressBar;
    private NavigationView navigationView;
    private NavigationView topNavigationView;
    private NavigationView bottomNavigationView;
    private NavigationPresenter navigationPresenter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity_layout);
        setToolbar();
        setProgressBar();
        ContextManager.getInstance().setContext(this);
    }

    public void onCreate(Bundle savedInstanceState, int layoutId) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        setToolbar();
        setProgressBar();
        ContextManager.getInstance().setContext(this);
    }

    public void setToolbar() {
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);
    }

    public void setLeftNavigationBar() {
        user = AuthorizationService.getInstance().getCurrentUser();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationPresenter = new NavigationPresenter(navigationView, getApplicationContext(), user);
        navigationPresenter.setUpNavigation();
    }

    public void setProgressBar() {
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void setTitle(int title) {
        mActionBarToolbar.setTitle(title);
    }

    public void startProgressBar() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    public void stopProgressBar() {
        this.progressBar.setVisibility(View.INVISIBLE);
    }

    public Toolbar getToolBar() {
        return mActionBarToolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }
}
