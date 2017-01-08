package com.example.vsokoltsov.uprogress.user.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.common.ScreenSizeHelper;
import com.example.vsokoltsov.uprogress.common.adapters.TestListAdapter;
import com.example.vsokoltsov.uprogress.common.helpers.ImageHelper;
import com.example.vsokoltsov.uprogress.navigation.NavigationDrawer;
import com.example.vsokoltsov.uprogress.user.current.User;
import com.squareup.picasso.Picasso;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 06.01.17.
 */

public class UserFragment extends Fragment {
    private View fragmentView;
    private User user;
    private ImageView userAvatar;
    private List<String> list = new ArrayList<String>();
    private NavigationDrawer navigationDrawer;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.user_fragment, container, false);
        loadList();
        loadUserImage();
        setElements();
        setToolbar();
        setNavigationDrawer();
        return fragmentView;
    }


    public void setUser(User user) {
        this.user = user;
    }

    private void setElements() {
        CollapsingToolbarLayout layout = (CollapsingToolbarLayout) fragmentView.findViewById(R.id.collapsing_toolbar);
        layout.setTitle(user.getCorrectName());
        FloatingActionButton floatingActionButton = (FloatingActionButton) fragmentView.findViewById(R.id.addDirection);
        floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_white));

    }

    private void loadUserImage() {
        userAvatar = (ImageView) fragmentView.findViewById(R.id.userAvatar);
        user = AuthorizationService.getInstance().getCurrentUser();
        Drawable emptyUser = ContextCompat.getDrawable(getContext(), R.drawable.empty_user);
        ImageHelper.getInstance(getContext()).load(user.getImage().getUrl(), userAvatar, emptyUser);
    }

    private void loadList() {
        for(int i = 0; i < 30; i++) {
            list.add("New item number " + i);
        }

        RecyclerView rv = (RecyclerView) fragmentView.findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        TestListAdapter adapter = new TestListAdapter(list, getActivity());
        rv.setAdapter(adapter);
    }

    private void setToolbar() {
        toolbar = (Toolbar) fragmentView.findViewById(R.id.toolbar_actionbar);
        ((ApplicationBaseActivity) getActivity()).setToolbar(toolbar);
    }

    private void setNavigationDrawer() {
        FragmentManager fragmentManager = (FragmentManager) getActivity().getSupportFragmentManager();
        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        navigationDrawer = (NavigationDrawer) fragmentManager.findFragmentById(R.id.navigation_drawer);
        navigationDrawer.setUp(R.id.navigation_drawer, drawerLayout);
    }
}
