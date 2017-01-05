package com.example.vsokoltsov.uprogress.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vsokoltsov.uprogress.R;

/**
 * Created by vsokoltsov on 06.01.17.
 */

public class StatisticsFragment extends Fragment {
    private View fragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.statistics_fragment, container, false);
        return fragmentView;
    }
}
