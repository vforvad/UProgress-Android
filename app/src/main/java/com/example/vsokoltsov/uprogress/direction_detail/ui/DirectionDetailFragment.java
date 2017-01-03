package com.example.vsokoltsov.uprogress.direction_detail.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.direction_detail.view.DirectionDetailView;
import com.example.vsokoltsov.uprogress.directions_list.models.Direction;

import org.w3c.dom.Text;

/**
 * Created by vsokoltsov on 29.11.16.
 */

public class DirectionDetailFragment extends Fragment implements DirectionDetailView {
    private View fragmentView;
    private ApplicationBaseActivity activity;
    private TextView directionDetailTitle;
    private TextView directionDetailRate;
    private TextView directionDetailDescription;
    private CheckBox checkbox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (ApplicationBaseActivity) getActivity();

        fragmentView = inflater.inflate(R.layout.direction_detail_fragment, container, false);
        directionDetailTitle = (TextView) fragmentView.findViewById(R.id.directionDetailTitle);
        directionDetailDescription = (TextView) fragmentView.findViewById(R.id.directionDetailDescription);
        directionDetailRate = (TextView) fragmentView.findViewById(R.id.directionDetailRate);
        checkbox = (CheckBox) fragmentView.findViewById(R.id.checkBox);
//        checkbox.setBackgroundResource(R.drawable.checkbox_checked);
//        checkbox.setButtonDrawable(R.drawable.checkbox_unchecked);
        return fragmentView;
    }

    @Override
    public void successResponse(Direction direction) {

    }

    @Override
    public void failureResponse(Throwable t) {

    }

    @Override
    public void startLoader() {

    }

    @Override
    public void stopLoader() {

    }
}
