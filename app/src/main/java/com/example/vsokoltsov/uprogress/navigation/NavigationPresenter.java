package com.example.vsokoltsov.uprogress.navigation;

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

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.authentication.ui.AuthorizationActivity;
import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.common.BaseApplication;
import com.example.vsokoltsov.uprogress.directions_list.ui.DirectionsActivity;
import com.example.vsokoltsov.uprogress.statistics.ui.StatisticsActivity;
import com.example.vsokoltsov.uprogress.user.current.User;

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

    private final Context context;
    private User currentUser;
    private View navHeader;

    TextView userEmail;
    TextView userName;
    ImageView imageView;

    public NavigationPresenter(NavigationView baseNavigationView,
                               DrawerLayout drawerLayout,
                               Context context,
                               User currentUser,
                               ActionBarDrawerToggle actionBarDrawerToggle) {
        this.baseNavigationView = baseNavigationView;
        this.drawerLayout = drawerLayout;
        this.context = context;
        this.currentUser = currentUser;
        this.toolbar = toolbar;
        this.actionBarDrawerToggle = actionBarDrawerToggle;
    }

    public void setUpNavigation() {
        setDrawerLayout();
        setUpTopNavigation();
        setUpFooterNavigation();
        setUpNavigationHeader();
        setNavigationItemListener();
    }

    private void setDrawerLayout() {
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    private void setUpTopNavigation() {
        int menu;
        topNavigationView = (NavigationView) baseNavigationView.findViewById(R.id.top_navigation);
        topNavigationView .setItemIconTintList(null);

        if (currentUser != null) {
            menu = R.menu.signed_navigation;
        }
        else {
            menu = R.menu.unsigned_navigation;
        }
        topNavigationView.inflateMenu(menu);
    }

    private void setUpFooterNavigation() {
        bottomNavigationView = (NavigationView) baseNavigationView.findViewById(R.id.bottom_navigation);
        bottomNavigationView .setItemIconTintList(null);

        if (currentUser == null) {
            bottomNavigationView.setVisibility(View.GONE);
        }
    }

    private void setUpNavigationHeader() {
        navHeader = topNavigationView.getHeaderView(0);

        if (currentUser == null) {
            navHeader.setVisibility(View.GONE);
        }
        else {
            setTextInfo();
            setImageInfo();
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
        ((BaseApplication) context.getApplicationContext()).getImageHelper().setUserImage(currentUser, imageView, R.drawable.empty_user);
    }

    private void setNavigationItemListener() {
        topNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.isChecked()) {
            item.setChecked(false);
        }
        else {
            item.setChecked(true);
        }
        drawerLayout.closeDrawers();

        switch(item.getItemId()) {
            case R.id.sign_in:
                Intent signInActivity = new Intent(context, AuthorizationActivity.class);
                signInActivity.putExtra("action", "sign_in");
                context.startActivity(signInActivity);
            case R.id.sign_up:
                Intent signUpActivity = new Intent(context, AuthorizationActivity.class);
                signUpActivity.putExtra("action", "sign_up");
                context.startActivity(signUpActivity);
            case R.id.directions:
                Intent dirActivity = new Intent(context, DirectionsActivity.class);
                context.startActivity(dirActivity);
                return true;
            case R.id.statistics:
                Intent statisticsActivity = new Intent(context, StatisticsActivity.class);
                context.startActivity(statisticsActivity);
                return true;
            default: return true;
        }
    }
}
