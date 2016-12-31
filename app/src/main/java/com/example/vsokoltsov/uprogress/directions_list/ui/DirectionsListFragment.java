package com.example.vsokoltsov.uprogress.directions_list.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.directions_list.DirectionsListAdapter;
import com.example.vsokoltsov.uprogress.user.User;
import com.example.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.example.vsokoltsov.uprogress.directions_list.models.Direction;
import com.example.vsokoltsov.uprogress.directions_list.models.DirectionModel;
import com.example.vsokoltsov.uprogress.directions_list.models.DirectionModelImpl;
import com.example.vsokoltsov.uprogress.directions_list.presenters.DirectionsListPresenter;
import com.example.vsokoltsov.uprogress.directions_list.presenters.DirectionsListPresenterImpl;
import com.example.vsokoltsov.uprogress.common.utils.ApiRequester;
import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.directions_list.DirectionListViewHolder;
import com.example.vsokoltsov.uprogress.directions_list.views.DirectionsListView;
import com.example.vsokoltsov.uprogress.directions_list.views.DirectionsListViewImpl;

import org.solovyev.android.views.llm.LinearLayoutManager;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 26.11.16.
 */

public class DirectionsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View fragmentView;
    private ApplicationBaseActivity activity;
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
    private DirectionsListPresenter presenter;

    private android.support.v4.app.FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        User user = AuthorizationService.getInstance().getCurrentUser();
        activity = (ApplicationBaseActivity) getActivity();

        fragmentView = inflater.inflate(R.layout.directions_list_fragment, container, false);
        rv = (RecyclerView) fragmentView.findViewById(R.id.directionsList);
        swipeLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.swipe_layout);
        swipeLayout.setOnRefreshListener(this);
        llm = new LinearLayoutManager(getActivity());
        final DirectionListViewHolder viewHolder = new DirectionListViewHolder(swipeLayout, rv, llm, this);

        final DirectionModel model = new DirectionModelImpl(viewHolder);
        final DirectionsListView view = new DirectionsListViewImpl(viewHolder);
        presenter = new DirectionsListPresenterImpl(view, model, user);
        presenter.onCreate((ApplicationBaseActivity) getActivity());
        setOnClickListener(viewHolder);
        setOnScrollListener();
        presenter.loadDirections();


//        progressBar = (ProgressBar) fragmentView.findViewById(R.id.progressBar);
//        rv.setHasFixedSize(true);

//        rv.setLayoutManager(llm);
//        setAdapter();
//        rv.setAdapter(adapter);
//        rv.setOnScrollListener(new RecyclerView.OnScrollListener(){
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (canLoad) {

//                }
//
//            }
//        });
//        loadDirectionsList();
        return fragmentView;
    }

    @Override
    public void onRefresh() {
        presenter.refreshList();
    }

//    private void setAdapter() {
//        adapter = new DirectionsListAdapter(directions, this);
//    }

//    private void loadDirectionsList() {
//        canLoad = false;
////        if (showMainLoader) {
////            activity.showProgress(R.string.loading);
////        }
//        progressBar.setVisibility(View.VISIBLE);
//        User currentUser = authManager.getCurrentUser();
//        Retrofit retrofit = api.getRestAdapter();
//        DirectionsApi service = retrofit.create(DirectionsApi.class);
//        service.getDirections(currentUser.getNick(), pageNumber)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<DirectionsList>() {
//                    @Override
//                    public void onCompleted() {
//                        swipeLayout.setRefreshing(false);
//                        progressBar.setVisibility(View.GONE);
//                        showMainLoader = true;
//                        canLoad = true;
//                        int firstVisibleItem = llm.findFirstVisibleItemPosition();
//                        firstVisibleInListview = firstVisibleItem;
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(DirectionsList directionsList) {
//                        setDirectionsList(directionsList);
//                    }
//                });
//    }
//
//    private void setDirectionsList(DirectionsList directions) {
//        if (!showMainLoader) {
//            adapter.directions = directions.getDirections();
//        }
//        else {
//            for(int i = 0; i < directions.getDirections().size(); i++) {
//                Direction d = (Direction) directions.getDirections().get(i);
//                adapter.directions.add(d);
//            }
//        }
//        adapter.notifyDataSetChanged();
//    }

//    @Override
//    public void onItemClicked(Direction direction) {
//        Intent detailIntent = new Intent(getActivity(), DirectionDetailActivity.class);
//        detailIntent.putExtra("direction", direction);
//        PendingIntent pendingIntent =
//                TaskStackBuilder.create(getActivity())
//                        // add all of DetailsActivity's parents to the stack,
//                        // followed by DetailsActivity itself
//                        .addNextIntentWithParentStack(detailIntent)
//                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
//        builder.setContentIntent(pendingIntent);
//        startActivity(detailIntent);
//        getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//    }
//
//    @Override
//    public void onRefresh() {
//        pageNumber = 1;
//        showMainLoader = false;
//        loadDirectionsList();
//    }

    private void setOnClickListener(DirectionListViewHolder viewHolder) {
        viewHolder.adapter.getPositionClicks()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Direction>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Direction direction) {


                    }
                });
    }

    private void setOnScrollListener() {
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                presenter.scrollDownListener(recyclerView, dx, dy);
            }
        });
    }

}
