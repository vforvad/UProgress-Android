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
import com.example.vsokoltsov.uprogress.views.directions.DirectionCompletionItemView;

import java.util.ArrayList;
import java.util.List;

import static com.example.vsokoltsov.uprogress.R.string.loading;

/**
 * Created by vsokoltsov on 27.11.16.
 */

public class DirectionsListAdapter extends RecyclerView.Adapter<DirectionsListAdapter.DirectionViewHolder> {
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
    public DirectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.direction_completion_view_item, parent, false);
        return new DirectionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DirectionViewHolder holder, int position) {
        holder.bind(directions.get(position));
    }


    public class DirectionViewHolder extends RecyclerView.ViewHolder {
        private DirectionCompletionItemView directionCompletionItemView;

        public DirectionViewHolder(View itemView) {
            super(itemView);
            directionCompletionItemView = (DirectionCompletionItemView) itemView;
        }

        public void bind(Direction direction) {
            directionCompletionItemView.bind(direction);
        }
    }

    @Override
    public int getItemCount() {
        return directions.size();
    }
}
