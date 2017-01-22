package com.example.vsokoltsov.uprogress.navigation;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.common.BaseApplication;
import com.example.vsokoltsov.uprogress.user.current.User;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vsokoltsov on 23.01.17.
 */

public class NavigationPresenter {
    private final NavigationView baseNavigationView;
    private NavigationView topNavigationView;
    private NavigationView bottomNavigationView;

    private final Context context;
    private User currentUser;
    private View navHeader;

    TextView userEmail;
    TextView userName;
    ImageView imageView;

    public NavigationPresenter(NavigationView baseNavigationView, Context context, User currentUser) {
        this.baseNavigationView = baseNavigationView;
        this.context = context;
        this.currentUser = currentUser;
    }

    public void setUpNavigation() {
        setUpTopNavigation();
        setUpFooterNavigation();
        setUpNavigationHeader();
    }

    private void setUpTopNavigation() {
        int menu;
        topNavigationView = (NavigationView) baseNavigationView.findViewById(R.id.top_navigation);
        topNavigationView .setItemIconTintList(null);

        if (currentUser != null) {
            menu = R.menu.signed_navigation;
        }
        else {
            menu = R.menu.unsigned_navigation;
        }
        topNavigationView.inflateMenu(menu);
    }

    private void setUpFooterNavigation() {
        bottomNavigationView = (NavigationView) baseNavigationView.findViewById(R.id.bottom_navigation);
        bottomNavigationView .setItemIconTintList(null);

        if (currentUser == null) {
            bottomNavigationView.setVisibility(View.GONE);
        }
    }

    private void setUpNavigationHeader() {
        navHeader = topNavigationView.getHeaderView(0);

        if (currentUser == null) {
            navHeader.setVisibility(View.GONE);
        }
        else {
            setTextInfo();
            setImageInfo();
        }
    }

    public void setCurrentUser(User user){
        this.currentUser = user;
    }

    private void setTextInfo(){
        TextView userName = (TextView) navHeader.findViewById(R.id.name);
        TextView userEmail = (TextView) navHeader.findViewById(R.id.email);

        if (currentUser.isFullNamePresent()) {
            userName.setText(currentUser.getCorrectName());
            userEmail.setText(currentUser.getNick());
        }
        else {
            userName.setText(currentUser.getNick());
        }
    }

    private void setImageInfo() {
        CircleImageView avatarView = (CircleImageView) navHeader.findViewById(R.id.circleView);
        ((BaseApplication) context.getApplicationContext()).getImageHelper().setUserImage(currentUser, avatarView, R.drawable.empty_user);
    }
}
