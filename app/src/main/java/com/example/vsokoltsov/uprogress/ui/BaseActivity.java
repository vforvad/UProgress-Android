package com.example.vsokoltsov.uprogress.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.api.UserApi;
import com.example.vsokoltsov.uprogress.models.authorization.AuthorizationService;
import com.example.vsokoltsov.uprogress.models.authorization.CurrentUser;
import com.example.vsokoltsov.uprogress.utils.ApiRequester;
import com.example.vsokoltsov.uprogress.ui.authorizations.AuthorizationActivity;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 22.11.16.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization_activity);
        currentUserRequest();
    }

    private void currentUserRequest() {
        Retrofit retrofit = ApiRequester.getInstance().getRestAdapter();
        UserApi service = retrofit.create(UserApi.class);
        service.getCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CurrentUser>() {
                    @Override
                    public void onCompleted() {
                        transitionToUsersList();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        transitionToUsersList();
                    }

                    @Override
                    public void onNext(CurrentUser user) {
                        currentUserReceived(user);
                    }
                });
    }

    private void currentUserReceived(CurrentUser user) {
        AuthorizationService auth = AuthorizationService.getInstance();
        Debug.waitForDebugger();
        auth.setCurrentUser(user.getUser());
    }

    private void transitionToUsersList() {
        Intent usersActivity = new Intent(this, AuthorizationActivity.class);
        startActivity(usersActivity);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }
}
