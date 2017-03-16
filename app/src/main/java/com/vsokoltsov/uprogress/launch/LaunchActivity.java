package com.vsokoltsov.uprogress.launch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.authentication.network.AuthenticationApi;
import com.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.vsokoltsov.uprogress.common.TabletActivity;
import com.vsokoltsov.uprogress.common.utils.ContextManager;
import com.vsokoltsov.uprogress.user.current.CurrentUser;
import com.vsokoltsov.uprogress.common.utils.ApiRequester;
import com.vsokoltsov.uprogress.authentication.ui.AuthorizationActivity;
import com.vsokoltsov.uprogress.user.current.CurrentUserManager;
import com.vsokoltsov.uprogress.user.current.CurrentUserModel;
import com.vsokoltsov.uprogress.user.current.CurrentUserView;
import com.vsokoltsov.uprogress.user.ui.UserActivity;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 22.11.16.
 */

public class LaunchActivity extends AppCompatActivity implements CurrentUserView{
    private boolean isTablet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization_activity);
        isTablet = getResources().getBoolean(R.bool.isTablet);
        ContextManager.getInstance().setContext(this);
        CurrentUserModel currentUserManager = new CurrentUserManager(getApplicationContext());
        LaunchPresenter presenter = new LaunchPresenter(this, currentUserManager);
        presenter.getCurrentUser();
//        currentUserRequest();
    }

    @Override
    public void currentUserReceived(CurrentUser currentUser) {
        AuthorizationService auth = AuthorizationService.getInstance();
        auth.setCurrentUser(currentUser.getUser());
    }

    @Override
    public void currentUserFailedToReceive(Throwable t) {

    }

    @Override
    public void completedCurrentUserRequest() {
        if (isTablet) {
            tabletActivity();
        }
        else {
            Intent usersActivity = new Intent(this, AuthorizationActivity.class);
            startActivity(usersActivity);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }
    }

    @Override
    public void redirectToProfile() {
        if (isTablet) {
            tabletActivity();
        }
        else {
            Intent profileActivity = new Intent(this, UserActivity.class);
            startActivity(profileActivity);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }
    }

    private void tabletActivity() {
        Intent tabletActivity = new Intent(this, TabletActivity.class);
        startActivity(tabletActivity);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }
}
