package com.vsokoltsov.uprogress.common;

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

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.authentication.messages.UserMessage;
import com.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.vsokoltsov.uprogress.navigation.NavigationDrawer;
import com.vsokoltsov.uprogress.navigation.NavigationPresenter;
import com.vsokoltsov.uprogress.common.utils.ContextManager;
import com.vsokoltsov.uprogress.navigation.NavigationViewItemsClick;
import com.vsokoltsov.uprogress.user.current.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by vsokoltsov on 22.11.16.
 */

public class ApplicationBaseActivity extends AppCompatActivity implements NavigationViewItemsClick {
    private Toolbar mActionBarToolbar;
    private ProgressBar progressBar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private NavigationPresenter navigationPresenter;
    private User user;
    private AuthorizationService authorizationService = AuthorizationService.getInstance();
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTablet = getResources().getBoolean(R.bool.isTablet);
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
        if (drawerLayout  != null) {
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
        user = AuthorizationService.getInstance().getCurrentUser();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationPresenter = new NavigationPresenter(navigationView, drawerLayout,
                this,
                user,
                getAcionBarToggler());
        navigationPresenter.setUpNavigation();
    }

    public void disableLeftNavigationBar() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
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

            if (isTablet) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowTitleEnabled(true);
            }
            else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
                getSupportActionBar().setHomeButtonEnabled(true);
            }


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

    public ActionBarDrawerToggle getAcionBarToggler() {
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
        if (drawerLayout != null) {
            drawerLayout.post(new Runnable() {
                @Override
                public void run() {
                    actionBarDrawerToggle.syncState();
                }
            });
            if (isTablet) {
                actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            }
        }
        return actionBarDrawerToggle;
    }

    public void setDetailViewToolbar() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.return_icon);
    }

    @Override
    public void signOut() {
        authorizationService.setCurrentUser(null);
        ((BaseApplication) getApplicationContext()).getPreferencesHelper().deleteToken();
        EventBus.getDefault().post(new UserMessage("signOut", authorizationService.getCurrentUser()));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    // This method will be called when a MessageEvent is posted
    @Subscribe
    public void onEvent(UserMessage event){
        switch (event.operationName){
            case "currentUser":
                navigationPresenter.setCurrentUser(event.user);
                navigationPresenter.setUpNavigation();
                break;
            case "signOut":
                navigationPresenter.setCurrentUser(event.user);
                navigationPresenter.setUpNavigation();
                break;
            default: break;
        }
    }
}
