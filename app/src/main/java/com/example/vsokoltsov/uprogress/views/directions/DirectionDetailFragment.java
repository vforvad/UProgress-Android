package com.example.vsokoltsov.uprogress.views.directions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.views.ApplicationBaseActivity;

import org.solovyev.android.views.llm.LinearLayoutManager;

/**
 * Created by vsokoltsov on 29.11.16.
 */

public class DirectionDetailFragment extends Fragment {
    private View fragmentView;
    private ApplicationBaseActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (ApplicationBaseActivity) getActivity();

        fragmentView = inflater.inflate(R.layout.direction_detail_fragment, container, false);
        return fragmentView;
    }
}
