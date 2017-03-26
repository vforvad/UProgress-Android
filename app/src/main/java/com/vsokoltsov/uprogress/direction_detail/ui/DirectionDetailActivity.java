package com.vsokoltsov.uprogress.direction_detail.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.directions_list.models.Direction;
import com.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.vsokoltsov.uprogress.directions_list.ui.DirectionsActivity;


/**
 * Created by vsokoltsov on 29.11.16.
 */

public class DirectionDetailActivity extends ApplicationBaseActivity {
    private android.support.v4.app.FragmentManager fragmentManager;
    private DirectionDetailFragment directionDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setDetailViewToolbar();
        super.disableLeftNavigationBar();
        fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        directionDetailFragment = new DirectionDetailFragment();
        fragmentTransaction.replace(R.id.main_content, directionDetailFragment);
        fragmentTransaction.commit();
    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        MenuInflater inflater = getMenuInflater();
//        directionDetailFragment.onCreateOptionsMenu(menu, inflater);
//        return true;
//    }
}
