package com.vsokoltsov.uprogress.statistics.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.common.ApplicationBaseActivity;

/**
 * Created by vsokoltsov on 06.01.17.
 */

public class StatisticsActivity extends ApplicationBaseActivity {
    private android.support.v4.app.FragmentManager fragmentManager;
    private StatisticsFragment statisticsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setLeftNavigationBar();
        fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        statisticsFragment = new StatisticsFragment();
        fragmentTransaction.replace(R.id.main_content, statisticsFragment);
        fragmentTransaction.commit();
    }
}
