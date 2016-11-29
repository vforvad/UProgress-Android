package com.example.vsokoltsov.uprogress.views.directions;

import android.os.Bundle;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.views.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.views.authorizations.AuthorizationBaseFragment;

/**
 * Created by vsokoltsov on 26.11.16.
 */

public class DirectionsActivity extends ApplicationBaseActivity {
    private android.support.v4.app.FragmentManager fragmentManager;
    private DirectionsListFragment directionsListFragment;
    private static boolean replaceFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization_activity);
        super.setToolbar();
        super.setLeftNavigationBar();
//        if (!replaceFragment) {
            fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            directionsListFragment= new DirectionsListFragment();
            fragmentTransaction.replace(R.id.main_content, directionsListFragment);
            fragmentTransaction.commit();
            replaceFragment = true;
//        }
    }
}
