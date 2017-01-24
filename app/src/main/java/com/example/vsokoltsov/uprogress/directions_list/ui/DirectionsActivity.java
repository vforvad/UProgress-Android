package com.example.vsokoltsov.uprogress.directions_list.ui;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 26.11.16.
 */

public class DirectionsActivity extends ApplicationBaseActivity {
    private android.support.v4.app.FragmentManager fragmentManager;
    private DirectionsListFragment directionsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setLeftNavigationBar();
        fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        directionsListFragment= new DirectionsListFragment();
        fragmentTransaction.replace(R.id.main_content, directionsListFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        directionsListFragment.onCreateOptionsMenu(menu, inflater);
        return true;
    }
}
