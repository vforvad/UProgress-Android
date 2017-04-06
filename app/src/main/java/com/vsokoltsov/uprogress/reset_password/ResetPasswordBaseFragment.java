package com.vsokoltsov.uprogress.reset_password;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vsokoltsov.uprogress.R;

/**
 * Created by vsokoltsov on 07.04.17.
 */

public class ResetPasswordBaseFragment extends Fragment {
    private View fragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentView = inflater.inflate(R.layout.reset_password_fragment, container, false);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        return fragmentView;
    }
}
