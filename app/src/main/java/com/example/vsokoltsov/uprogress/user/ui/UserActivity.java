package com.example.vsokoltsov.uprogress.user.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.common.ScreenSizeHelper;
import com.example.vsokoltsov.uprogress.common.adapters.TestListAdapter;
import com.example.vsokoltsov.uprogress.common.helpers.ImageHelper;
import com.example.vsokoltsov.uprogress.directions_list.adapters.DirectionsListAdapter;
import com.example.vsokoltsov.uprogress.directions_list.ui.DirectionsListFragment;
import com.example.vsokoltsov.uprogress.user.current.User;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.solovyev.android.views.llm.LinearLayoutManager;

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
        super.setLeftNavigationBar();
        for(int i = 0; i < 30; i++) {
            list.add("New item number " + i);
        }
        userAvatar = (ImageView) findViewById(R.id.userAvatar);

        user = AuthorizationService.getInstance().getCurrentUser();
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.addDirection);
        floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_white));
        ScreenSizeHelper helper = new ScreenSizeHelper(getWindow());

        Drawable emptyUser = ContextCompat.getDrawable(this, R.drawable.empty_user);

        ImageHelper.getInstance(this).load(user.getImage().getUrl(), userAvatar, emptyUser);
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        TestListAdapter adapter = new TestListAdapter(list, this);
        rv.setAdapter(adapter);
//        fragmentManager = getSupportFragmentManager();
//        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        userFragment = new UserFragment();
//        fragmentTransaction.replace(R.id.main_content, userFragment);
//        fragmentTransaction.commit();
    }
}
