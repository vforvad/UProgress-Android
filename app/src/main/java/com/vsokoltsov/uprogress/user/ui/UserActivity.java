package com.vsokoltsov.uprogress.user.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.vsokoltsov.uprogress.user.current.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 06.01.17.
 */

public class UserActivity extends ApplicationBaseActivity {
    private android.support.v4.app.FragmentManager fragmentManager;
    private UserFragment userFragment;
    private User user;
    private ImageView userAvatar;
    private List<String> list = new ArrayList<String>();
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.user_activity_layout);
        user = AuthorizationService.getInstance().getCurrentUser();
        fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        userFragment = new UserFragment();
        userFragment.setUser(user);
        fragmentTransaction.replace(R.id.main_content, userFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        userFragment.onCreateOptionsMenu(menu, inflater);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        userFragment.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        userFragment.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        userFragment.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        userFragment.onActivityResult(requestCode, resultCode, data);
    }
}
