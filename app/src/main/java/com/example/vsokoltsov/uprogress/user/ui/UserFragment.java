package com.example.vsokoltsov.uprogress.user.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.example.vsokoltsov.uprogress.common.ScreenSizeHelper;
import com.example.vsokoltsov.uprogress.common.adapters.TestListAdapter;
import com.example.vsokoltsov.uprogress.common.helpers.ImageHelper;
import com.example.vsokoltsov.uprogress.user.current.User;
import com.squareup.picasso.Picasso;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.user_fragment, container, false);
        loadList();
//        loadUserImage();
//        setElements();
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

        user = AuthorizationService.getInstance().getCurrentUser();
        RecyclerView rv = (RecyclerView) fragmentView.findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        TestListAdapter adapter = new TestListAdapter(list, getActivity());
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
