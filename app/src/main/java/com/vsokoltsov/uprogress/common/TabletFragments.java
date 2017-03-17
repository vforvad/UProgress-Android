package com.vsokoltsov.uprogress.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.vsokoltsov.uprogress.authentication.ui.AuthorizationBaseFragment;
import com.vsokoltsov.uprogress.user.current.User;
import com.vsokoltsov.uprogress.user.ui.UserFragment;

/**
 * Created by vsokoltsov on 17.03.17.
 */

public class TabletFragments {
    private FragmentManager fragmentManager;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;

    TabletFragments(FragmentManager manager) {
        this.fragmentManager = manager;
        this.fragmentTransaction = fragmentManager.beginTransaction();
    }

    public void showProfile(User user) {
        UserFragment frg = new UserFragment();
        frg.setUser(user);
        commitTransaction(frg);
    }

    public void shoAuthorizationProfile(String action) {
        Bundle arguments = new Bundle();
        AuthorizationBaseFragment fragment = new AuthorizationBaseFragment();
        arguments.putString("action", action);
        commitTransaction(fragment);
    }


    private void commitTransaction(Fragment frg) {
        fragmentTransaction.replace(R.id.main_content, frg);
        fragmentTransaction.commit();
    }
}
