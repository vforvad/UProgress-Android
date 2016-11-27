package com.example.vsokoltsov.uprogress.views.directions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.adapters.DirectionsListAdapter;
import com.example.vsokoltsov.uprogress.interfaces.DirectionsApi;
import com.example.vsokoltsov.uprogress.models.User;
import com.example.vsokoltsov.uprogress.models.authorization.AuthorizationService;
import com.example.vsokoltsov.uprogress.models.directions.Direction;
import com.example.vsokoltsov.uprogress.models.directions.DirectionsList;
import com.example.vsokoltsov.uprogress.utils.ApiRequester;
import com.example.vsokoltsov.uprogress.views.ApplicationBaseActivity;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 26.11.16.
 */

public class DirectionsListFragment extends Fragment {
    private View fragmentView;
    private ApplicationBaseActivity activity;
    private List<Direction> courses = new ArrayList<Direction>();
    private RecyclerView rv;
    private DirectionsListAdapter adapter;
    private ApiRequester api = ApiRequester.getInstance();
    private AuthorizationService authManager = AuthorizationService.getInstance();

    private android.support.v4.app.FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (ApplicationBaseActivity) getActivity();

        fragmentView = inflater.inflate(R.layout.directions_list_fragment, container, false);
        rv = (RecyclerView) fragmentView.findViewById(R.id.directionsList);
        setAdapter();
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        loadDirectionsList();
        return fragmentView;
    }

    private void setAdapter() {
        adapter = new DirectionsListAdapter(courses, getActivity());
    }

    private void loadDirectionsList() {
        User currentUser = authManager.getCurrentUser();
        Retrofit retrofit = api.getRestAdapter();
        DirectionsApi service = retrofit.create(DirectionsApi.class);
        service.getDirections(currentUser.getNick())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DirectionsList>() {
                    @Override
                    public void onCompleted() {
//                        swipeLayout.setRefreshing(false);
//                        activity.dismissProgress();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DirectionsList directionsList) {
                        setDirectionsList(directionsList);
                    }
                });
    }

    private void setDirectionsList(DirectionsList directions) {
        adapter.directions = directions.getDirections();
        adapter.notifyDataSetChanged();
    }
}
