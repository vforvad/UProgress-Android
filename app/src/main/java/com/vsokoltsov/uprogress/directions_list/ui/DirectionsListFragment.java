package com.vsokoltsov.uprogress.directions_list.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.common.BaseApplication;
import com.vsokoltsov.uprogress.common.ErrorHandler;
import com.vsokoltsov.uprogress.common.adapters.BaseListAdapterInterface;
import com.vsokoltsov.uprogress.common.services.ErrorResponse;
import com.vsokoltsov.uprogress.common.utils.RetrofitException;
import com.vsokoltsov.uprogress.direction_detail.popup.AddStepForm;
import com.vsokoltsov.uprogress.direction_detail.popup.PopupInterface;
import com.vsokoltsov.uprogress.direction_detail.ui.DirectionDetailActivity;
import com.vsokoltsov.uprogress.directions_list.adapters.DirectionsListAdapter;
import com.vsokoltsov.uprogress.directions_list.models.DirectionRequest;
import com.vsokoltsov.uprogress.directions_list.models.DirectionsList;
import com.vsokoltsov.uprogress.directions_list.popup.DirectionsListPopup;
import com.vsokoltsov.uprogress.user.current.User;
import com.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.vsokoltsov.uprogress.directions_list.models.Direction;
import com.vsokoltsov.uprogress.directions_list.models.DirectionModel;
import com.vsokoltsov.uprogress.directions_list.models.DirectionModelImpl;
import com.vsokoltsov.uprogress.directions_list.presenters.DirectionsListPresenter;
import com.vsokoltsov.uprogress.directions_list.presenters.DirectionsListPresenterImpl;
import com.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.vsokoltsov.uprogress.directions_list.views.DirectionsListView;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 26.11.16.
 */

public class DirectionsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        DirectionsListView, BaseListAdapterInterface,
        android.support.v7.widget.SearchView.OnQueryTextListener,
        SearchView.OnQueryTextListener, PopupInterface {
    private BaseApplication baseApplication;
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
    private DirectionsListPopup formFragment;
    private Direction pickedDirection;
    private ErrorHandler errorHandler;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        baseApplication = ((BaseApplication) getActivity().getApplicationContext());
        user = AuthorizationService.getInstance().getCurrentUser();
        activity = (ApplicationBaseActivity) getActivity();
        activity.setTitle(getResources().getString(R.string.directions_title));
        fragmentView = inflater.inflate(R.layout.directions_list_fragment, container, false);
        errorHandler = new ErrorHandler(getActivity());
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
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formFragment = new DirectionsListPopup();
                formFragment.setPopupInterface(DirectionsListFragment.this);
                formFragment.show(getFragmentManager(), "dialog");
            }
        });
    }

    private void setComponents() {
        final DirectionModel model = new DirectionModelImpl(getActivity().getApplicationContext());
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
        pickedDirection = direction;
        Resources resource = getResources();
        final CharSequence[] items = {
                resource.getString(R.string.directions_list_menu_edit)
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(getResources().getString(R.string.directions_list_menu_title));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch(item) {
                    case 0:
                        dialog.dismiss();
                        Bundle arguments = new Bundle();
                        arguments.putParcelable("direction", direction);
                        formFragment = new DirectionsListPopup();
                        formFragment.setArguments(arguments);
                        formFragment.setPopupInterface(DirectionsListFragment.this);
                        formFragment.show(getFragmentManager(), "dialog");
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
        MenuItem addItem = menu.findItem(R.id.addItem);
        addItem.setVisible(false);
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
    public void successDirectionCreation(Direction direction) {
        formFragment.dismiss();
        adapter.items.add(0, direction);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void failedDirectionCreation(Throwable t) {
       directionOperationInvalid(t);
    }

    @Override
    public void successUpdateDirection(Direction direction) {
        pickedDirection = null;
        formFragment.dismiss();
        for(int i = 0; i < adapter.items.size(); i++) {
            Direction item = (Direction) adapter.items.get(i);
            if (item.getId() == direction.getId()) {
                adapter.items.add(i, direction);
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void failedUpdateDirection(Throwable t) {
       directionOperationInvalid(t);
    }

    @Override
    public void successDeleteDirection(Direction direction) {

    }

    @Override
    public void failedDeleteDirection(Throwable t) {

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

    @Override
    public void successPopupOperation(Object obj, boolean operation) {
        DirectionRequest request = (DirectionRequest) obj;
        if (operation) {
            presenter.createDirection(user.getNick(), request);
        }
        else {
            presenter.updateDirection(user.getId(), pickedDirection.getId(), request);
        }
    }

    @Override
    public void failedPopupOperation(Throwable t) {
        errorHandler.showMessage(t);
    }

    private void directionOperationInvalid(Throwable t) {
        try {
            RetrofitException error = (RetrofitException) t;
            ErrorResponse errors = null;
            errors = error.getErrorBodyAs(ErrorResponse.class);
            formFragment.titleWrapper.setError(errors.getFullErrorMessage("title"));
            formFragment.descriptionWrapper.setError(errors.getFullErrorMessage("description"));
        } catch (IOException e) {
            errorHandler.showMessage(t);
        }
    }
}
