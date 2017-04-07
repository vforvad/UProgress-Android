package com.vsokoltsov.uprogress.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.authentication.ui.AuthorizationActivity;
import com.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.vsokoltsov.uprogress.common.BaseApplication;
import com.vsokoltsov.uprogress.common.NavigationFragments;
import com.vsokoltsov.uprogress.common.TabletFragments;
import com.vsokoltsov.uprogress.directions_list.ui.DirectionsActivity;
import com.vsokoltsov.uprogress.statistics.ui.StatisticsActivity;
import com.vsokoltsov.uprogress.user.current.User;
import com.vsokoltsov.uprogress.user.ui.UserActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vsokoltsov on 23.01.17.
 */

public class NavigationPresenter implements NavigationView.OnNavigationItemSelectedListener {
    private final NavigationView baseNavigationView;
    private final DrawerLayout drawerLayout;
    private NavigationView topNavigationView;
    private NavigationView bottomNavigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationViewItemsClick itemsClick;

    private final Context context;
    private User currentUser;
    private View navHeader;
    private boolean isTablet;
    private TabletFragments tabletFragments;
    private NavigationFragments navigationFragments;

    TextView userEmail;
    TextView userName;
    ImageView imageView;

    public NavigationPresenter(NavigationView baseNavigationView,
                               DrawerLayout drawerLayout,
                               NavigationViewItemsClick clicksInterface,
                               User currentUser,
                               ActionBarDrawerToggle actionBarDrawerToggle) {
        this.baseNavigationView = baseNavigationView;
        this.drawerLayout = drawerLayout;
        this.context = (Context) clicksInterface;
        this.itemsClick = clicksInterface;
        this.currentUser = currentUser;
        this.toolbar = toolbar;
        this.actionBarDrawerToggle = actionBarDrawerToggle;
        this.isTablet = context.getResources().getBoolean(R.bool.isTablet);
        this.tabletFragments = new TabletFragments(((ApplicationBaseActivity) context).getSupportFragmentManager());
        this.navigationFragments = new NavigationFragments(context);
    }

    public void setUpNavigation() {
        setDrawerLayout();
        setUpTopNavigation();
        setUpFooterNavigation();
        setUpNavigationHeader();
        setNavigationItemListener();
    }

    private void setDrawerLayout() {
        if (actionBarDrawerToggle != null && drawerLayout != null) {
            drawerLayout.setDrawerListener(actionBarDrawerToggle);
        }
    }

    private void setUpTopNavigation() {
        int menu;
        topNavigationView = (NavigationView) baseNavigationView.findViewById(R.id.top_navigation);
        topNavigationView .setItemIconTintList(null);

        if (currentUser != null) {
            topNavigationView.getMenu().setGroupVisible(R.id.unsignedUser, false);
            topNavigationView.getMenu().setGroupVisible(R.id.signedUser, true);
        }
        else {
            topNavigationView.getMenu().setGroupVisible(R.id.unsignedUser, true);
            topNavigationView.getMenu().setGroupVisible(R.id.signedUser, false);
        }
    }

    private void setUpFooterNavigation() {
        bottomNavigationView = (NavigationView) baseNavigationView.findViewById(R.id.bottom_navigation);
        bottomNavigationView .setItemIconTintList(null);

        if (currentUser == null) {
            bottomNavigationView.setVisibility(View.GONE);
        }
        else {
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }

    private void setUpNavigationHeader() {
        navHeader = topNavigationView.getHeaderView(0);

        if (currentUser == null) {
            navHeader.setVisibility(View.GONE);
        }
        else {
            navHeader.setVisibility(View.VISIBLE);
            setTextInfo();
            setImageInfo();
            navHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isTablet) {
                        tabletFragments.showProfile(currentUser);
                    }
                    else {
                        Intent userActivity = new Intent(context, UserActivity.class);
                        context.startActivity(userActivity);
                    }
                }
            });
        }
    }

    public void setCurrentUser(User user){
        this.currentUser = user;
    }

    private void setTextInfo(){
        userName = (TextView) navHeader.findViewById(R.id.name);
        userEmail = (TextView) navHeader.findViewById(R.id.email);

        if (currentUser.isFullNamePresent()) {
            userName.setText(currentUser.getCorrectName());
            userEmail.setText(currentUser.getNick());
        }
        else {
            userName.setText(currentUser.getNick());
        }
    }

    private void setImageInfo() {
        imageView = (CircleImageView) navHeader.findViewById(R.id.circleView);
        ((BaseApplication) context.getApplicationContext()).getImageHelper().setUserImage(context, currentUser, imageView, R.drawable.ic_empty_user);
    }

    private void setNavigationItemListener() {
        topNavigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.isChecked()) {
            item.setChecked(false);
        }
        else {
            item.setChecked(true);
        }
        if (drawerLayout != null) {
            drawerLayout.closeDrawers();
        }

        switch(item.getItemId()) {
            case R.id.sign_in:
                if (isTablet) {
                    tabletFragments.shoAuthorizationProfile("sign_in");
                }
                else {
                    navigationFragments.signIn("sign_in");
                }

            case R.id.sign_up:
                if (isTablet) {
                    tabletFragments.shoAuthorizationProfile("sign_up");
                }
                else {
                    navigationFragments.signIn("sign_up");
                }

                return true;
            case R.id.directions:
                if (isTablet) {
                    tabletFragments.directionsList();
                }
                else {
                    Intent dirActivity = new Intent(context, DirectionsActivity.class);
                    context.startActivity(dirActivity);
                }
                return true;
            case R.id.statistics:
                if (isTablet) {
                    tabletFragments.statistisFragment();
                }
                else {
                    Intent statisticsActivity = new Intent(context, StatisticsActivity.class);
                    context.startActivity(statisticsActivity);
                }
                return true;
            case R.id.sign_out:
                itemsClick.signOut();
                return true;
            default: return true;
        }
    }
}
