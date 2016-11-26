package com.example.vsokoltsov.uprogress.views.directions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.views.ApplicationBaseActivity;

/**
 * Created by vsokoltsov on 26.11.16.
 */

public class DirectionsListFragment extends Fragment {
    private View fragmentView;
    private ApplicationBaseActivity activity;

    private android.support.v4.app.FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (ApplicationBaseActivity) getActivity();

        fragmentView = inflater.inflate(R.layout.directions_list_fragment, container, false);
        return fragmentView;
    }
}
