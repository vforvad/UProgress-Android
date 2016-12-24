package com.example.vsokoltsov.uprogress.ui.directions;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.adapters.DirectionsListAdapter;
import com.example.vsokoltsov.uprogress.api.DirectionsApi;
import com.example.vsokoltsov.uprogress.interfaces.DirectionItemClickListener;
import com.example.vsokoltsov.uprogress.models.User;
import com.example.vsokoltsov.uprogress.models.authorization.AuthorizationService;
import com.example.vsokoltsov.uprogress.models.directions.Direction;
import com.example.vsokoltsov.uprogress.models.directions.DirectionsList;
import com.example.vsokoltsov.uprogress.utils.ApiRequester;
import com.example.vsokoltsov.uprogress.ui.ApplicationBaseActivity;

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

public class DirectionsListFragment extends Fragment implements DirectionItemClickListener, SwipeRefreshLayout.OnRefreshListener  {
    private View fragmentView;
    private ApplicationBaseActivity activity;
    private List<Direction> directions = new ArrayList<Direction>();
    private RecyclerView rv;
    private DirectionsListAdapter adapter;
    private ApiRequester api = ApiRequester.getInstance();
    private AuthorizationService authManager = AuthorizationService.getInstance();
    private SwipeRefreshLayout swipeLayout;
    private Boolean showMainLoader = true;
    private int pageNumber = 1;
    private LinearLayoutManager llm;
    private boolean canLoad = true;
    private static int firstVisibleInListview;
    private ProgressBar progressBar;



    private android.support.v4.app.FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (ApplicationBaseActivity) getActivity();

        fragmentView = inflater.inflate(R.layout.directions_list_fragment, container, false);
        swipeLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.swipe_layout);
        swipeLayout.setOnRefreshListener(this);
        progressBar = (ProgressBar) fragmentView.findViewById(R.id.progressBar);
        rv = (RecyclerView) fragmentView.findViewById(R.id.directionsList);
        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        setAdapter();
        rv.setAdapter(adapter);
        rv.setOnScrollListener(new RecyclerView.OnScrollListener(){
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (canLoad) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastLayoutPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                    int itemsCount = layoutManager.getItemCount() - 2;
                    if (lastLayoutPosition == itemsCount && dy > 0) {
                        pageNumber++;
                        loadDirectionsList();
                        firstVisibleInListview = lastLayoutPosition;
                    }
                }

            }
        });
        loadDirectionsList();
        return fragmentView;
    }

    private void setAdapter() {
        adapter = new DirectionsListAdapter(directions, this);
    }

    private void loadDirectionsList() {
        canLoad = false;
//        if (showMainLoader) {
//            activity.showProgress(R.string.loading);
//        }
        progressBar.setVisibility(View.VISIBLE);
        User currentUser = authManager.getCurrentUser();
        Retrofit retrofit = api.getRestAdapter();
        DirectionsApi service = retrofit.create(DirectionsApi.class);
        service.getDirections(currentUser.getNick(), pageNumber)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DirectionsList>() {
                    @Override
                    public void onCompleted() {
                        swipeLayout.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                        showMainLoader = true;
                        canLoad = true;
                        int firstVisibleItem = llm.findFirstVisibleItemPosition();
                        firstVisibleInListview = firstVisibleItem;
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
        if (!showMainLoader) {
            adapter.directions = directions.getDirections();
        }
        else {
            for(int i = 0; i < directions.getDirections().size(); i++) {
                Direction d = (Direction) directions.getDirections().get(i);
                adapter.directions.add(d);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(Direction direction) {
        Intent detailIntent = new Intent(getActivity(), DirectionDetailActivity.class);
        detailIntent.putExtra("direction", direction);
        PendingIntent pendingIntent =
                TaskStackBuilder.create(getActivity())
                        // add all of DetailsActivity's parents to the stack,
                        // followed by DetailsActivity itself
                        .addNextIntentWithParentStack(detailIntent)
                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
        builder.setContentIntent(pendingIntent);
        startActivity(detailIntent);
        getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        showMainLoader = false;
        loadDirectionsList();
    }
}
