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
import android.widget.ProgressBar;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.direction_detail.popup.PopupInterface;
import com.example.vsokoltsov.uprogress.user.adapters.UserInfoListAdapter;
import com.example.vsokoltsov.uprogress.common.helpers.ImageHelper;
import com.example.vsokoltsov.uprogress.navigation.NavigationDrawer;
import com.example.vsokoltsov.uprogress.user.current.User;
import com.example.vsokoltsov.uprogress.user.current.UserItem;
import com.example.vsokoltsov.uprogress.user.current.UserRequest;
import com.example.vsokoltsov.uprogress.user.models.UserModel;
import com.example.vsokoltsov.uprogress.user.models.UserModelImpl;
import com.example.vsokoltsov.uprogress.user.popup.UserFormPopup;
import com.example.vsokoltsov.uprogress.user.presenters.UserProfilePresenter;
import com.example.vsokoltsov.uprogress.user.presenters.UserProfilePresenterImpl;
import com.example.vsokoltsov.uprogress.user.views.UserProfileView;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 06.01.17.
 */

public class UserFragment extends Fragment implements PopupInterface, UserProfileView {
    private View fragmentView;
    private User user;
    private ApplicationBaseActivity activity;
    private ImageView userAvatar;
    private List<UserItem> list = new ArrayList<UserItem>();
    private NavigationDrawer navigationDrawer;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private UserFormPopup popup;
    private ProgressBar progressBar;
    UserProfilePresenter presenter;
    private UserInfoListAdapter adapter;
    CollapsingToolbarLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.user_fragment, container, false);
        activity = (ApplicationBaseActivity) getActivity();
        UserModel model = new UserModelImpl();
        presenter = new UserProfilePresenterImpl(this, model);
        loadList();
        loadUserImage();
        setElements();
        setToolbar();
        setNavigationDrawer();
        setProgressBar();
        return fragmentView;
    }


    public void setUser(User user) {
        this.user = user;
    }

    private void setElements() {
        layout = (CollapsingToolbarLayout) fragmentView.findViewById(R.id.collapsing_toolbar);
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
        ImageHelper.getInstance(getContext()).setUserImage(user, userAvatar, R.drawable.empty_user);
    }

    private void loadList() {
        RecyclerView rv = (RecyclerView) fragmentView.findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        adapter = new UserInfoListAdapter(list);
        adapter.setList(user);
        rv.setAdapter(adapter);
    }

    private void setToolbar() {
        toolbar = (Toolbar) fragmentView.findViewById(R.id.toolbar_actionbar);
        ((ApplicationBaseActivity) getActivity()).setToolbar(toolbar);
    }

    private void setNavigationDrawer() {
        activity.setLeftNavigationBar();
    }

    private void setProgressBar() {
        progressBar =  (ProgressBar) fragmentView.findViewById(R.id.progressBar);
    }

    @Override
    public void successPopupOperation(Object obj) {
        UserRequest request = (UserRequest) obj;
        presenter.updateUser(user.getNick(), request);
    }

    @Override
    public void failedPopupOperation(Throwable t) {

    }

    @Override
    public void successUpdate(User user) {
        this.user = user;
        AuthorizationService.getInstance().setCurrentUser(user);
        popup.dismiss();
        layout.setTitle(user.getCorrectName());
        adapter.setList(user);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void failedUpdate(Throwable t) {

    }

    @Override
    public void startLoader() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoader() {
        progressBar.setVisibility(View.GONE);
    }
}
