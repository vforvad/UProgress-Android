package com.example.vsokoltsov.uprogress.directions_list.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.direction_detail, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchItem.setIcon(R.drawable.search);
        return true;
    }
}
