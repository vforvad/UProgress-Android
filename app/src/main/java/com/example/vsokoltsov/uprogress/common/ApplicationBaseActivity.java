package com.example.vsokoltsov.uprogress.common;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.example.vsokoltsov.uprogress.navigation.NavigationDrawer;
import com.example.vsokoltsov.uprogress.navigation.NavigationPresenter;
import com.example.vsokoltsov.uprogress.common.utils.ContextManager;
import com.example.vsokoltsov.uprogress.user.current.User;

/**
 * Created by vsokoltsov on 22.11.16.
 */

public class ApplicationBaseActivity extends AppCompatActivity {
    private Toolbar mActionBarToolbar;
    private ProgressBar progressBar;
    private DrawerLayout drawerLayout;
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
        defaultToolbar(mActionBarToolbar);
    }

    public void setLeftNavigationBar() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        user = AuthorizationService.getInstance().getCurrentUser();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationPresenter = new NavigationPresenter(navigationView, drawerLayout,
                this,
                user,
                getAcionBarToggler());
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
        defaultToolbar(toolbar);
    }

    private void defaultToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private ActionBarDrawerToggle getAcionBarToggler() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                mActionBarToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                actionBarDrawerToggle.syncState();
            }
        });
        return actionBarDrawerToggle;
    }

    public void setDetailViewToolbar() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.return_icon);
    }
}
