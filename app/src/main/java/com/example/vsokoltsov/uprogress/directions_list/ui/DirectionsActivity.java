package com.example.vsokoltsov.uprogress.directions_list.ui;

import android.os.Bundle;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;

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
