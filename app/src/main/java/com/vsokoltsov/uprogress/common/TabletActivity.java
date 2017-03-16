package com.vsokoltsov.uprogress.common;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.vsokoltsov.uprogress.authentication.ui.AuthorizationBaseFragment;
import com.vsokoltsov.uprogress.common.utils.ContextManager;
import com.vsokoltsov.uprogress.direction_detail.ui.DirectionDetailFragment;
import com.vsokoltsov.uprogress.navigation.NavigationPresenter;
import com.vsokoltsov.uprogress.user.current.User;
import com.vsokoltsov.uprogress.user.ui.UserFragment;

import java.lang.reflect.Constructor;

/**
 * Created by vsokoltsov on 17.03.17.
 */

public class TabletActivity extends ApplicationBaseActivity {
    private String defaultFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setLeftNavigationBar();
        if (AuthorizationService.getInstance().getCurrentUser() != null) {
            renderFragment(UserFragment.class, null);
        }
        else {
            Bundle arguments = new Bundle();
            arguments.putString("action", "sign_in");
            renderFragment(AuthorizationBaseFragment.class,arguments);
        }
    }

    public void renderFragment(Class cl, Bundle arguments){
        fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        try {
            Fragment frg = (Fragment) cl.newInstance();
            if (arguments != null) {
                frg.setArguments(arguments);
            }
            fragmentTransaction.replace(R.id.main_content, frg);
            fragmentTransaction.commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
