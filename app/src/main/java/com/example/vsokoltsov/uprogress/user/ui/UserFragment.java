package com.example.vsokoltsov.uprogress.user.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.example.vsokoltsov.uprogress.common.ScreenSizeHelper;
import com.example.vsokoltsov.uprogress.user.current.User;
import com.squareup.picasso.Picasso;

/**
 * Created by vsokoltsov on 06.01.17.
 */

public class UserFragment extends Fragment {
    private View fragmentView;
    private User user;
    private ImageView userAvatar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.user_fragment, container, false);
        user = AuthorizationService.getInstance().getCurrentUser();
//        userAvatar = (ImageView) fragmentView.findViewById(R.id.userAvatar);
//        ScreenSizeHelper helper = new ScreenSizeHelper(getActivity().getWindow());
//        Picasso.with(getContext())
//                .load(user.getImage().getUrl())
//                .resize(helper.getScreenWidth(), helper.getScreenWidth() / 2)
//                .into(userAvatar);
        return fragmentView;
    }
}
