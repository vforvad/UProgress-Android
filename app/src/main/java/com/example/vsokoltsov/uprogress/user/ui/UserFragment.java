package com.example.vsokoltsov.uprogress.user.ui;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import com.example.vsokoltsov.uprogress.direction_detail.popup.PopupInterface;
import com.example.vsokoltsov.uprogress.directions_list.popup.DirectionsListPopup;
import com.example.vsokoltsov.uprogress.directions_list.ui.DirectionsListFragment;
import com.example.vsokoltsov.uprogress.user.adapters.UserInfoListAdapter;
import com.example.vsokoltsov.uprogress.common.helpers.ImageHelper;
import com.example.vsokoltsov.uprogress.navigation.NavigationDrawer;
import com.example.vsokoltsov.uprogress.user.current.User;
import com.example.vsokoltsov.uprogress.user.current.UserItem;
import com.example.vsokoltsov.uprogress.user.popup.UserFormPopup;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 06.01.17.
 */

public class UserFragment extends Fragment implements PopupInterface {
    private View fragmentView;
    private User user;
    private ApplicationBaseActivity activity;
    private ImageView userAvatar;
    private List<UserItem> list = new ArrayList<UserItem>();
    private NavigationDrawer navigationDrawer;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private UserFormPopup popup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.user_fragment, container, false);
        activity = (ApplicationBaseActivity) getActivity();
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
        floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.edit_icon));
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.price_green)));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup = new UserFormPopup();
                popup.setPopupInterface(UserFragment.this);
                popup.setUser(user);
                popup.show(getActivity().getFragmentManager(), "form");
            }
        });

    }

    private void loadUserImage() {
        userAvatar = (ImageView) fragmentView.findViewById(R.id.userAvatar);
        user = AuthorizationService.getInstance().getCurrentUser();
        Drawable emptyUser = ContextCompat.getDrawable(getContext(), R.drawable.empty_user);
        ImageHelper.getInstance(getContext()).load(user.getImage().getUrl(), userAvatar, emptyUser);
    }

    private void loadList() {
        list.add(new UserItem("Email", user.getEmail()));
        list.add(new UserItem("Nick", user.getNick()));
        list.add(new UserItem("Location", user.getLocation()));
        list.add(new UserItem("Description", user.getDescription()));

        RecyclerView rv = (RecyclerView) fragmentView.findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        UserInfoListAdapter adapter = new UserInfoListAdapter(list);
        rv.setAdapter(adapter);
    }

    private void setToolbar() {
        toolbar = (Toolbar) fragmentView.findViewById(R.id.toolbar_actionbar);
        ((ApplicationBaseActivity) getActivity()).setToolbar(toolbar);
    }

    private void setNavigationDrawer() {
        activity.setLeftNavigationBar();
    }

    @Override
    public void successPopupOperation(Object obj) {

    }

    @Override
    public void failedPopupOperation(Throwable t) {

    }
}
