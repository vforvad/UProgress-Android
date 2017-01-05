package com.example.vsokoltsov.uprogress.user.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.example.vsokoltsov.uprogress.user.current.User;

/**
 * Created by vsokoltsov on 06.01.17.
 */

public class UserFragment extends Fragment {
    private View fragmentView;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.user_fragment, container, false);
        user = AuthorizationService.getInstance().getCurrentUser();
        return fragmentView;
    }
}
