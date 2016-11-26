package com.example.vsokoltsov.uprogress.views;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.interfaces.UserApi;
import com.example.vsokoltsov.uprogress.messages.UserMessage;
import com.example.vsokoltsov.uprogress.models.authorization.AuthorizationService;
import com.example.vsokoltsov.uprogress.models.authorization.CurrentUser;
import com.example.vsokoltsov.uprogress.utils.ApiRequester;
import com.example.vsokoltsov.uprogress.views.navigation.NavigationDrawer;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 22.11.16.
 */

public class ApplicationBaseActivity extends AppCompatActivity {
    private Toolbar mActionBarToolbar;
    private NavigationDrawer mNavigationDrawerFragment;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void currentUserRequest() {
        Retrofit retrofit = ApiRequester.getInstance().getRestAdapter();
        UserApi service = retrofit.create(UserApi.class);
        service.getCurrentUser()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CurrentUser>() {
                    @Override
                    public void onCompleted() {
//                        dismissProgress();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CurrentUser user) {
                        currentUserReceived(user);
                    }
                });
    }

    public void currentUserReceived(CurrentUser user) {
        AuthorizationService auth = AuthorizationService.getInstance();
        auth.setCurrentUser(user.getUser());
        EventBus.getDefault().post(new UserMessage("currentUser", user.getUser()));
    }

    public void setToolbar() {
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);
    }

    public void setLeftNavigationBar() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawerFragment = (NavigationDrawer) fragmentManager.findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, drawerLayout);
    }
}
