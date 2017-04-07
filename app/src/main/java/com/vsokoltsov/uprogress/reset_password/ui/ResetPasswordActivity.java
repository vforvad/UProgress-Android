package com.vsokoltsov.uprogress.reset_password.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.vsokoltsov.uprogress.direction_detail.ui.DirectionDetailFragment;
import com.vsokoltsov.uprogress.directions_list.ui.DirectionsActivity;
import com.vsokoltsov.uprogress.reset_password.ResetPasswordBaseFragment;

/**
 * Created by vsokoltsov on 07.04.17.
 */

public class ResetPasswordActivity extends ApplicationBaseActivity {
    private android.support.v4.app.FragmentManager fragmentManager;
    private ResetPasswordBaseFragment resetPasswordBaseFragment;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setDetailViewToolbar();
        super.disableLeftNavigationBar();
        getExtras();
        Bundle arguments = new Bundle();
        arguments.putString("reset_token", token);
        fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        resetPasswordBaseFragment = new ResetPasswordBaseFragment();
        resetPasswordBaseFragment.setArguments(arguments);
        fragmentTransaction.replace(R.id.main_content, resetPasswordBaseFragment);
        fragmentTransaction.commit();
    }

    private void getExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            token = (String) extras.getString("reset_token");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, DirectionsActivity.class));
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
