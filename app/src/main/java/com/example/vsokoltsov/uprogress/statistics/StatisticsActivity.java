package com.example.vsokoltsov.uprogress.statistics;

import android.os.Bundle;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.user.ui.UserFragment;

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
