package com.example.vsokoltsov.uprogress.adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.interfaces.OnLoadMoreListener;
import com.example.vsokoltsov.uprogress.models.directions.Direction;
import com.example.vsokoltsov.uprogress.view_holders.DirectionViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.example.vsokoltsov.uprogress.R.string.loading;

/**
 * Created by vsokoltsov on 27.11.16.
 */

public class DirectionsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<Direction> directions = new ArrayList<Direction>();
    private Activity activity;
    public Fragment fragment;

    private final int VISIBLE_THRESHOLD = 6;

    private final int ITEM_VIEW_TYPE_BASIC = 0;
    private final int ITEM_VIEW_TYPE_FOOTER = 1;

    private int firstVisibleItem, visibleItemCount, totalItemCount, previousTotal = 0;
    private boolean loading = true;

    public DirectionsListAdapter(List<Direction> directions, Activity activity) {
        this.directions = directions;
        this.activity = activity;
    }

    public DirectionsListAdapter(List<Direction> directions, Fragment fragment) {
        this.directions = directions;
        this.fragment = fragment;
    }

    public DirectionsListAdapter(List<Direction> directions, Fragment fragment, RecyclerView recyclerView,
                     final OnLoadMoreListener onLoadMoreListener){
        this.directions = directions;
        this.fragment= fragment;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    totalItemCount = linearLayoutManager.getItemCount();
                    visibleItemCount = linearLayoutManager.getChildCount();
                    firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!loading && (totalItemCount - visibleItemCount)
                            <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
                        // End has been reached

//                        addDirection(null);
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    public void addDirection(Direction direction) {
        if (!directions.contains(direction)) {
            directions.add(direction);
            notifyItemInserted(directions.size() - 1);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.direction_list_item, parent, false);
        DirectionViewHolder viewHolder = new DirectionViewHolder(v, this);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DirectionViewHolder directionViewHolder = (DirectionViewHolder) holder;
        Direction direction = directions.get(position);

        directionViewHolder.setDirection(direction);
        directionViewHolder.directionTitle.setText(direction.getTitle());
        directionViewHolder.directionPercents.setText(String.format("%d", direction.getPercentsResult()));
        directionViewHolder.directionRation.setText(direction.getFinishedStepsRation());

//        if (course.getImage() != null) {
//            String fullURL = course.getImage().getUrl();
//            Picasso.with(this.activity.getApplicationContext())
//                    .load(fullURL)
//                    .placeholder(emptyUser)
//                    .into(courseViewHolder.courseImage);
//        }
//        else {
//            courseViewHolder.courseImage.setImageDrawable(emptyUser);
//        }
    }




    @Override
    public int getItemCount() {
        return directions.size();
    }
}
