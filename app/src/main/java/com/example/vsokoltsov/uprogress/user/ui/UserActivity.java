package com.example.vsokoltsov.uprogress.user.ui;

import android.os.Bundle;
import android.view.View;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.directions_list.ui.DirectionsListFragment;

/**
 * Created by vsokoltsov on 06.01.17.
 */

public class UserActivity extends ApplicationBaseActivity {
    private android.support.v4.app.FragmentManager fragmentManager;
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.user_activity_layout);
        super.setLeftNavigationBar();
        setTitle(null);
        fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        userFragment = new UserFragment();
        fragmentTransaction.replace(R.id.main_content, userFragment);
        fragmentTransaction.commit();
    }
}
