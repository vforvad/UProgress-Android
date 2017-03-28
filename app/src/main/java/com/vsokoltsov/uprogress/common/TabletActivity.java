package com.vsokoltsov.uprogress.common;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;

import com.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.vsokoltsov.uprogress.directions_list.ui.DirectionsActivity;


/**
 * Created by vsokoltsov on 17.03.17.
 */

public class TabletActivity extends ApplicationBaseActivity {
    private TabletFragments tabletFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setLeftNavigationBar();
        tabletFragments = new TabletFragments(getSupportFragmentManager());
        if (AuthorizationService.getInstance().getCurrentUser() != null) {
            tabletFragments.showProfile(AuthorizationService.getInstance().getCurrentUser());
        } else {
            tabletFragments.shoAuthorizationProfile("sign_in");
        }
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() >= 1) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }

}
