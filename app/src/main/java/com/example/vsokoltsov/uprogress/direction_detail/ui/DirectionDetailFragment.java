package com.example.vsokoltsov.uprogress.direction_detail.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.common.adapters.BaseListAdapterInterface;
import com.example.vsokoltsov.uprogress.direction_detail.adapters.StepsListAdapter;
import com.example.vsokoltsov.uprogress.direction_detail.model.DirectionDetailModel;
import com.example.vsokoltsov.uprogress.direction_detail.model.DirectionDetailModelImpl;
import com.example.vsokoltsov.uprogress.direction_detail.model.steps.Step;
import com.example.vsokoltsov.uprogress.direction_detail.presenter.DirectionDetailPresenter;
import com.example.vsokoltsov.uprogress.direction_detail.presenter.DirectionDetailPresenterImpl;
import com.example.vsokoltsov.uprogress.direction_detail.view.DirectionDetailListAdapter;
import com.example.vsokoltsov.uprogress.direction_detail.view.DirectionDetailView;
import com.example.vsokoltsov.uprogress.directions_list.models.Direction;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 29.11.16.
 */

public class DirectionDetailFragment extends Fragment implements DirectionDetailView, DirectionDetailListAdapter,
        SwipeRefreshLayout.OnRefreshListener {
    private View fragmentView;
    private ApplicationBaseActivity activity;
    private TextView directionDetailTitle;
    private TextView directionDetailRate;
    private TextView directionDetailDescription;
    private CheckBox checkbox;
    private DirectionDetailPresenter presenter;
    String directionId;
    String userNick;

    private RecyclerView rv;
    private StepsListAdapter adapter;
    private SwipeRefreshLayout swipeLayout;
    private LinearLayoutManager llm;
    private List<Step> steps = new ArrayList<Step>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (ApplicationBaseActivity) getActivity();
        fragmentView = inflater.inflate(R.layout.direction_detail_fragment, container, false);
        getExtras();
        setComponents();
        setElements();
        setOnCheckedListeners();
        presenter.loadDirection(userNick, directionId);
        return fragmentView;
    }

    private void setComponents() {
        DirectionDetailModel model = new DirectionDetailModelImpl();
        presenter = new DirectionDetailPresenterImpl(model, this);
    }

    private void getExtras() {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            userNick = extras.getString("user");
            directionId = extras.getString("direction");
        }
    }

    private void setElements() {
        directionDetailTitle = (TextView) fragmentView.findViewById(R.id.directionDetailTitle);
        directionDetailDescription = (TextView) fragmentView.findViewById(R.id.directionDetailDescription);
        directionDetailRate = (TextView) fragmentView.findViewById(R.id.directionDetailRate);

        rv = (RecyclerView) fragmentView.findViewById(R.id.stepsList);
        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        adapter = new StepsListAdapter(steps, rv, this);
        rv.setAdapter(adapter);

        swipeLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.swipe_layout);
        swipeLayout.setOnRefreshListener(this);
    }

    private void setOnCheckedListeners() {

    }

    @Override
    public void successResponse(Direction direction) {
        directionDetailTitle.setText(direction.getTitle());
        directionDetailDescription.setText(direction.getDescription());
        directionDetailRate.setText(Integer.toString(direction.getPercentsResult()));
        adapter.items = direction.getSteps();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void failureResponse(Throwable t) {

    }

    @Override
    public void startLoader() {
        activity.startProgressBar();
    }

    @Override
    public void stopLoader() {
        activity.stopProgressBar();
    }

    @Override
    public void loadMore() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onCheckboxChanged(Step step, boolean value) {

    }
}
