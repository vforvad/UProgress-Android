package com.example.vsokoltsov.uprogress.user.ui;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.user.current.User;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.user_activity_layout);
        user = AuthorizationService.getInstance().getCurrentUser();
//        super.setLeftNavigationBar();
        loadUserImage();
        setElements();
//        super.setLeftNavigationBar();
//        for(int i = 0; i < 30; i++) {
//            list.add("New item number " + i);
//        }
//        userAvatar = (ImageView) findViewById(R.id.userAvatar);
//        CollapsingToolbarLayout layout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//

//        layout.setTitle(user.getCorrectName());
//        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.addDirection);
//        floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_white));
//        ScreenSizeHelper helper = new ScreenSizeHelper(getWindow());
//
//        Drawable emptyUser = ContextCompat.getDrawable(this, R.drawable.empty_user);
//
//        ImageHelper.getInstance(this).load(user.getImage().getUrl(), userAvatar, emptyUser);
//        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView);
//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        rv.setLayoutManager(llm);
//
//        TestListAdapter adapter = new TestListAdapter(list, this);
//        rv.setAdapter(adapter);
        fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        userFragment = new UserFragment();
        userFragment.setUser(user);
        fragmentTransaction.replace(R.id.main_content, userFragment);
        fragmentTransaction.commit();
    }

    private void setElements() {
//        CollapsingToolbarLayout layout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        layout.setTitle(user.getCorrectName());
//        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.addDirection);
//        floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_white));

    }

    private void loadUserImage() {
//        userAvatar = (ImageView) findViewById(R.id.userAvatar);
//        user = AuthorizationService.getInstance().getCurrentUser();
//        Drawable emptyUser = ContextCompat.getDrawable(this, R.drawable.empty_user);
//        ImageHelper.getInstance(this).load(user.getImage().getUrl(), userAvatar, emptyUser);
    }
}
