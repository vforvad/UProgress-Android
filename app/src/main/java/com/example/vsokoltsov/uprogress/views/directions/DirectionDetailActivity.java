package com.example.vsokoltsov.uprogress.views.directions;

import android.os.Bundle;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.views.ApplicationBaseActivity;

/**
 * Created by vsokoltsov on 29.11.16.
 */

public class DirectionDetailActivity extends ApplicationBaseActivity {
    private android.support.v4.app.FragmentManager fragmentManager;
    private DirectionsListFragment directionsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.direction_detail_activity);
        super.setToolbar();
        super.setLeftNavigationBar();
        fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        directionsListFragment= new DirectionsListFragment();
        fragmentTransaction.replace(R.id.main_content, directionsListFragment);
        fragmentTransaction.commit();
    }
}
