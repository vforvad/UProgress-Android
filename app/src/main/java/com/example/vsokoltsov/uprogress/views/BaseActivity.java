package com.example.vsokoltsov.uprogress.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.utils.ApiRequester;
import com.example.vsokoltsov.uprogress.views.authorizations.AuthorizationActivity;

/**
 * Created by vsokoltsov on 22.11.16.
 */

public class BaseActivity extends AppCompatActivity {
    private ApiRequester api = ApiRequester.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api.setContext(getApplicationContext());
        setContentView(R.layout.authorization_activity);
        Intent usersActivity = new Intent(this, AuthorizationActivity.class);
        startActivity(usersActivity);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

}
