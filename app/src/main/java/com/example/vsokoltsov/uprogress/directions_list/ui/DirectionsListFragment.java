package com.example.vsokoltsov.uprogress.directions_list.ui;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.common.SwipeableRecyclerViewTouchListener;
import com.example.vsokoltsov.uprogress.common.adapters.BaseListAdapterInterface;
import com.example.vsokoltsov.uprogress.direction_detail.ui.DirectionDetailActivity;
import com.example.vsokoltsov.uprogress.directions_list.adapters.DirectionsListAdapter;
import com.example.vsokoltsov.uprogress.directions_list.models.DirectionsList;
import com.example.vsokoltsov.uprogress.user.User;
import com.example.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.example.vsokoltsov.uprogress.directions_list.models.Direction;
import com.example.vsokoltsov.uprogress.directions_list.models.DirectionModel;
import com.example.vsokoltsov.uprogress.directions_list.models.DirectionModelImpl;
import com.example.vsokoltsov.uprogress.directions_list.presenters.DirectionsListPresenter;
import com.example.vsokoltsov.uprogress.directions_list.presenters.DirectionsListPresenterImpl;
import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.directions_list.views.DirectionsListView;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 26.11.16.
 */

public class DirectionsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        DirectionsListView, BaseListAdapterInterface, android.support.v7.widget.SearchView.OnQueryTextListener, SearchView.OnQueryTextListener {
    private View fragmentView;
    private ApplicationBaseActivity activity;
    private List<Direction> directions = new ArrayList<Direction>();
    private RecyclerView rv;
    private DirectionsListAdapter adapter;
    private SwipeRefreshLayout swipeLayout;
    private LinearLayoutManager llm;
    private DirectionsListPresenter presenter;
    private User user;
    private FloatingActionButton floatingActionButton;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        user = AuthorizationService.getInstance().getCurrentUser();
        activity = (ApplicationBaseActivity) getActivity();
        fragmentView = inflater.inflate(R.layout.directions_list_fragment, container, false);
        setElements();
        setComponents();
        presenter.loadDirections();
        return fragmentView;
    }

    private void setElements() {
        setButton();
        rv = (RecyclerView) fragmentView.findViewById(R.id.directionsList);
        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        adapter = new DirectionsListAdapter(directions, rv, this);
        rv.setAdapter(adapter);

        swipeLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.swipe_layout);
        swipeLayout.setOnRefreshListener(this);
    }

    public void setButton() {
        floatingActionButton = (FloatingActionButton) fragmentView.findViewById(R.id.addDirection);
        floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_white));
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.price_green)));
    }

    private void setComponents() {
        final DirectionModel model = new DirectionModelImpl();
        presenter = new DirectionsListPresenterImpl(this, model, user);
        setOnClickListener();
    }

    @Override
    public void onRefresh() {
        presenter.refreshList();
    }

    private void setOnClickListener() {
        adapter.getPositionClicks()
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
                        Intent directionDetailActivity = new Intent(getActivity(), DirectionDetailActivity.class);
                        directionDetailActivity.putExtra("user", user.getNick());
                        directionDetailActivity.putExtra("direction", Integer.toString(direction.getId()));
                        startActivity(directionDetailActivity);
                        getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                    }
                });

        adapter.getLongClick()
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
                        selectedItemAction(direction);
                    }
                });
    }

    private void selectedItemAction(Direction direction) {
        Resources resource = getResources();
        final CharSequence[] items = {
                resource.getString(R.string.directions_list_menu_edit),
                resource.getString(R.string.directions_list_menu_delete)
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(getResources().getString(R.string.directions_list_menu_title));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch(item) {
                    case 0:
                        break;
                    case 1:
                        break;
                    default:
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.direction_detail, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setIconified(true);
        searchItem.setIcon(R.drawable.search);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void successResponse(DirectionsList list) {
        for(int i = 0; i < list.getDirections().size(); i++) {
            Direction d = list.getDirections().get(i);
            adapter.items.add(d);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void failedResponse(Throwable t){
        t.printStackTrace();
    }

    @Override
    public void refreshList(DirectionsList list) {
        adapter.items = list.getDirections();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void startRefreshing() {
        swipeLayout.setRefreshing(true);
    }

    @Override
    public void stopRefreshing() {
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void startFooterLoader() {
        adapter.addDirection(null);
    }

    @Override
    public void stopFooterLoader() {
        adapter.removeItem(null);
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
        presenter.loadMoreDirections();
    }

    @Override
    public String getSearchAttribute(Object obj) {
        return ((Direction) obj).getTitle();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return true;
    }
}
