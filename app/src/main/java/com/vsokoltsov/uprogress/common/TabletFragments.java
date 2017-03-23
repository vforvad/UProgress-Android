package com.vsokoltsov.uprogress.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.vsokoltsov.uprogress.authentication.ui.AuthorizationBaseFragment;
import com.vsokoltsov.uprogress.directions_list.ui.DirectionsListFragment;
import com.vsokoltsov.uprogress.user.current.User;
import com.vsokoltsov.uprogress.user.ui.UserFragment;

/**
 * Created by vsokoltsov on 17.03.17.
 */

public class TabletFragments {
    private FragmentManager fragmentManager;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;

    public TabletFragments(FragmentManager manager) {
        this.fragmentManager = manager;
    }

    public void showProfile(User user) {
        this.fragmentTransaction = fragmentManager.beginTransaction();
        UserFragment frg = new UserFragment();
        frg.setUser(user);
        commitTransaction(frg);
    }

    public void shoAuthorizationProfile(String action) {
        this.fragmentTransaction = fragmentManager.beginTransaction();
        Bundle arguments = new Bundle();
        AuthorizationBaseFragment fragment = new AuthorizationBaseFragment();
        arguments.putString("action", action);
        commitTransaction(fragment);
    }

    public void directionsList() {
        this.fragmentTransaction = fragmentManager.beginTransaction();
        DirectionsListFragment frg = new DirectionsListFragment();
        commitTransaction(frg);
    }


    private void commitTransaction(Fragment frg) {
        fragmentTransaction.replace(R.id.main_content, frg);
        fragmentTransaction.commit();
    }
}
