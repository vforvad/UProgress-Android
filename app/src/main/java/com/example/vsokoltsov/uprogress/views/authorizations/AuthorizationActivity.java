package com.example.vsokoltsov.uprogress.views.authorizations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.views.ApplicationBaseActivity;

public class AuthorizationActivity extends ApplicationBaseActivity{

    private android.support.v4.app.FragmentManager fragmentManager;
    public AuthorizationBaseFragment authorizationBaseFragment;
    private Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization_activity);
        setToolbar();
        fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        authorizationBaseFragment = new AuthorizationBaseFragment();
        fragmentTransaction.replace(R.id.main_content, authorizationBaseFragment);
        fragmentTransaction.commit();
    }

    private void setToolbar() {
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);
    }
}
