package com.vsokoltsov.uprogress.direction_detail.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.vsokoltsov.uprogress.common.BaseApplication;
import com.vsokoltsov.uprogress.common.ErrorHandler;
import com.vsokoltsov.uprogress.common.SwipeableRecyclerViewTouchListener;
import com.vsokoltsov.uprogress.common.TabletActivity;
import com.vsokoltsov.uprogress.common.TabletFragments;
import com.vsokoltsov.uprogress.common.helpers.MessagesHelper;
import com.vsokoltsov.uprogress.common.services.ErrorResponse;
import com.vsokoltsov.uprogress.common.utils.RetrofitException;
import com.vsokoltsov.uprogress.direction_detail.adapters.StepsListAdapter;
import com.vsokoltsov.uprogress.direction_detail.model.DirectionDetailModel;
import com.vsokoltsov.uprogress.direction_detail.model.DirectionDetailModelImpl;
import com.vsokoltsov.uprogress.direction_detail.model.steps.Step;
import com.vsokoltsov.uprogress.direction_detail.model.steps.StepRequest;
import com.vsokoltsov.uprogress.direction_detail.popup.AddStepForm;
import com.vsokoltsov.uprogress.direction_detail.popup.PopupInterface;
import com.vsokoltsov.uprogress.direction_detail.popup.StepDialogFragment;
import com.vsokoltsov.uprogress.direction_detail.presenter.DirectionDetailPresenter;
import com.vsokoltsov.uprogress.direction_detail.presenter.DirectionDetailPresenterImpl;
import com.vsokoltsov.uprogress.direction_detail.view.DirectionDetailListAdapter;
import com.vsokoltsov.uprogress.direction_detail.view.DirectionDetailView;
import com.vsokoltsov.uprogress.directions_list.models.Direction;
import com.vsokoltsov.uprogress.directions_list.ui.DirectionsActivity;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 29.11.16.
 */

public class DirectionDetailFragment extends Fragment implements DirectionDetailView, DirectionDetailListAdapter,
        SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener, PopupInterface {
    BaseApplication baseApplication;
    private View fragmentView;
    private ApplicationBaseActivity activity;
    private TextView directionDetailTitle;
    private TextView directionDetailRate;
    private TextView directionDetailDescription;
    private TextView directionUpdatedAt;
    private DirectionDetailPresenter presenter;
    String directionId;
    String userNick;
    MessagesHelper messagesHelper;
    AddStepForm formDialog;
    private ErrorHandler errorHandler;

    private RecyclerView rv;
    private StepsListAdapter adapter;
    private SwipeRefreshLayout swipeLayout;
    private LinearLayoutManager llm;
    private List<Step> steps = new ArrayList<Step>();
    private TabletFragments tabletFragments;

    private boolean isTablet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        baseApplication = ((BaseApplication) getActivity().getApplicationContext());
        activity = (ApplicationBaseActivity) getActivity();
        fragmentView = inflater.inflate(R.layout.direction_detail_fragment, container, false);
        tabletFragments = new TabletFragments(getFragmentManager());
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet) {
            ((TabletActivity) getActivity()).setToolbar();
            ((TabletActivity) getActivity()).getToolBar().setNavigationIcon(R.drawable.return_icon);
            ((TabletActivity) getActivity()).getToolBar().setTitle(getResources().getString(R.string.direction_title));
        }
        else {
            activity.setTitle(getResources().getString(R.string.direction_title));
        }
        messagesHelper = new MessagesHelper(getResources());
        errorHandler = new ErrorHandler(activity);
        getExtras();
        setComponents();
        setElements();
        setOnClickListeners();
        setOnCheckedListeners();
        presenter.loadDirection(userNick, directionId);
        return fragmentView;
    }

    private void setOnClickListeners() {
        adapter.getLongClick()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Step>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Step step) {
                        StepDialogFragment fragment = new StepDialogFragment();
                        fragment.setStep(step);
                        fragment.show(getActivity().getFragmentManager(), "dialog");
                    }
                });
    }

    private void setComponents() {
        DirectionDetailModel model = new DirectionDetailModelImpl(getActivity().getApplicationContext());
        presenter = new DirectionDetailPresenterImpl(model, this);
    }

    private void getExtras() {
        Bundle extras;
        if (isTablet) {
            extras = getArguments();
        }
        else {
            extras = getActivity().getIntent().getExtras();
        }

        if (extras != null) {
            userNick = extras.getString("user");
            directionId = extras.getString("direction");
        }
    }

    private void setElements() {
        directionDetailTitle = (TextView) fragmentView.findViewById(R.id.directionDetailTitle);
        directionDetailDescription = (TextView) fragmentView.findViewById(R.id.directionDetailDescription);
        directionDetailRate = (TextView) fragmentView.findViewById(R.id.directionDetailRate);
        directionUpdatedAt = (TextView) fragmentView.findViewById(R.id.directionUpdatedAt);

        rv = (RecyclerView) fragmentView.findViewById(R.id.stepsList);
        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        adapter = new StepsListAdapter(steps, rv, this);
        rv.setAdapter(adapter);

        swipeLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.swipe_layout);
        swipeLayout.setOnRefreshListener(this);
        setSwipeListener();
    }

    private void setSwipeListener() {
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(rv,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {

                            @Override
                            public boolean canSwipeLeft(int position) {
                                return true;
                            }

                            @Override
                            public boolean canSwipeRight(int position) {
                                return false;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    Step step = (Step) adapter.items.get(position);
                                    presenter.deleteStep(userNick, directionId, Integer.toString(step.getId()), reverseSortedPositions);
                                    adapter.items.remove(position);
                                    adapter.notifyItemRemoved(position);
                                }
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {

                            }
                        });

        rv.addOnItemTouchListener(swipeTouchListener);
    }

    private void setOnCheckedListeners() {

    }

    @Override
    public void successResponse(Direction direction) {
        directionDetailTitle.setText(direction.getTitle());
        directionDetailDescription.setText(direction.getDescription());
        directionDetailRate.setText(Integer.toString(direction.getPercentsResult()));

        if (direction.getUpdatedAt() != null) {
            long now = System.currentTimeMillis();
            String date = (String) DateUtils.getRelativeTimeSpanString(direction.getUpdatedAt().getTime(), now, DateUtils.DAY_IN_MILLIS);
            directionUpdatedAt.setText(date);
        }
        adapter.items = direction.getSteps();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void failureResponse(Throwable t) {
        errorHandler.showMessage(t);
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
    public void successStepUpdate(Step step) {
        adapter.updateElement(step);

        Toast.makeText(getContext(), messagesHelper.messageForStepUpdate(step),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void failureStepUpdate(Throwable t) {
        Toast.makeText(getContext(), getResources().getString(R.string.steps_failed_update_message),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStartRefresh() {
        swipeLayout.setRefreshing(true);
    }

    @Override
    public void onStopRefresh() {
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void successDelete(Step step, int[] positions) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void failedDelete(Throwable t) {
        Toast.makeText(getContext(), getResources().getString(R.string.steps_failed_update_message),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void successStepCreate(Step step) {
        formDialog.dismiss();
        adapter.items.add(step);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void failedStepCreate(Throwable t) {
        try {
            RetrofitException error = (RetrofitException) t;
            ErrorResponse errors = null;
            errors = error.getErrorBodyAs(ErrorResponse.class);
            formDialog.titleWrapper.setError(errors.getFullErrorMessage("title"));
            formDialog.descriptionWrapper.setError(errors.getFullErrorMessage("description"));
        } catch (IOException e) {
            errorHandler.showMessage(t);
        }

    }

    @Override
    public void onRefresh() {
        presenter.reloadDirection(userNick, directionId);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.direction_detail, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (isTablet) {
                    tabletFragments.directionsList();
                }
                return true;
            case R.id.addItem:
                createStep();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCheckboxChanged(Step step, boolean value) {
        String stepId = Integer.toString(step.getId());
        StepRequest request = new StepRequest(
          step.getTitle(),
          step.getDescription(),
          value
        );
        presenter.updateStep(userNick, directionId, stepId, request);
    }

    @Override
    public void loadMore() {

    }

    @Override
    public String getSearchAttribute(Object obj) {
        return ((Step) obj).getTitle();
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
        StepRequest request = (StepRequest) obj;
        presenter.createStep(userNick, directionId, request);
    }

    @Override
    public void failedPopupOperation(Throwable t) {
        errorHandler.showMessage(t);
    }

    public void createStep() {
        formDialog = new AddStepForm();
        formDialog.setPopupInterface(this);
        formDialog.show(getActivity().getFragmentManager(), "dialog");
    }
}
