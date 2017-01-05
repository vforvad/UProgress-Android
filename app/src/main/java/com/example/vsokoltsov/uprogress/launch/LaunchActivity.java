package com.example.vsokoltsov.uprogress.launch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.authentication.network.AuthenticationApi;
import com.example.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.example.vsokoltsov.uprogress.common.utils.ContextManager;
import com.example.vsokoltsov.uprogress.user.current.CurrentUser;
import com.example.vsokoltsov.uprogress.common.utils.ApiRequester;
import com.example.vsokoltsov.uprogress.authentication.ui.AuthorizationActivity;
import com.example.vsokoltsov.uprogress.user.current.CurrentUserManager;
import com.example.vsokoltsov.uprogress.user.current.CurrentUserModel;
import com.example.vsokoltsov.uprogress.user.current.CurrentUserView;
import com.example.vsokoltsov.uprogress.user.ui.UserActivity;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 22.11.16.
 */

public class LaunchActivity extends AppCompatActivity implements CurrentUserView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization_activity);
        ContextManager.getInstance().setContext(this);
        CurrentUserModel currentUserManager = new CurrentUserManager();
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
        Intent usersActivity = new Intent(this, AuthorizationActivity.class);
        startActivity(usersActivity);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    @Override
    public void redirectToProfile() {
        Intent profileActivity = new Intent(this, UserActivity.class);
        startActivity(profileActivity);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }
}
