package com.example.vsokoltsov.uprogress.directions_list;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.common.interfaces.OnLoadMoreListener;
import com.example.vsokoltsov.uprogress.directions_list.models.Direction;
import com.example.vsokoltsov.uprogress.directions_list.ui.DirectionCompletionItemView;
import com.example.vsokoltsov.uprogress.directions_list.views.DirectionsListView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by vsokoltsov on 27.11.16.
 */

public class DirectionsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnCreateContextMenuListener {
    public List<Direction> directions = new ArrayList<Direction>();
    private Activity activity;
    public Fragment fragment;
    public DirectionsListView view;

    private final int ITEM_VIEW_TYPE_BASIC = 0;
    private final int ITEM_VIEW_TYPE_FOOTER = 1;

    private int firstVisibleItem, visibleItemCount, totalItemCount, previousTotal = 0;

    private final PublishSubject<Direction> onClickSubject = PublishSubject.create();
    private final PublishSubject<Direction> onLongClickSubject = PublishSubject.create();

    public DirectionsListAdapter(List<Direction> directions, Activity activity) {
        this.directions = directions;
        this.activity = activity;
    }

    public DirectionsListAdapter(List<Direction> directions, Fragment fragment) {
        this.directions = directions;
        this.fragment = fragment;
    }

    public DirectionsListAdapter(List<Direction> directions, RecyclerView recyclerView,
                     final DirectionsListView view){
        this.directions = directions;
        this.view = view;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    totalItemCount = linearLayoutManager.getItemCount();
                    visibleItemCount = linearLayoutManager.getChildCount();
                    firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                    int firstNumber = totalItemCount - visibleItemCount - 1;
                    if (firstNumber <= firstVisibleItem) {
                        // End has been reached

                        if (view != null) {
                            view.onLoadMore();
                        }
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
        if (viewType == ITEM_VIEW_TYPE_BASIC) {
            return onCreateBasicView(parent, viewType);
        }
        else if (viewType == ITEM_VIEW_TYPE_FOOTER) {
            return onCreateFooterView(parent, viewType);
        }
        else {
            throw new IllegalStateException("Invalid type, this type ot items " + viewType + " can't be handled");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_VIEW_TYPE_BASIC) {
            onBindBasicView(holder, position);
        } else {
            onBindFooterView(holder, position);
        }
    }


    private RecyclerView.ViewHolder onCreateBasicView(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.direction_completion_view_item, parent, false);
        DirectionViewHolder viewHolder = new DirectionViewHolder(v);
        viewHolder.directionCompletionItemView.setOnCreateContextMenuListener(this);
        return viewHolder;
    }

    private RecyclerView.ViewHolder onCreateFooterView(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.progress_bar, parent, false);
        return new FooterViewHolder(v);
    }

    private void onBindBasicView(RecyclerView.ViewHolder holder, int position) {
        Direction direction = directions.get(position);
        ((DirectionViewHolder) holder).bind(direction);
        ((DirectionViewHolder) holder).directionCompletionItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(direction);
            }
        });

        ((DirectionViewHolder) holder).directionCompletionItemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongClickSubject.onNext(direction);
                return true;
            }
        });
    }

    private void onBindFooterView(RecyclerView.ViewHolder genericHolder, int position) {
        ((FooterViewHolder) genericHolder).progressBar.setIndeterminate(true);
    }

    public Observable<Direction> getPositionClicks(){
        return onClickSubject.asObservable();
    }

    public Observable<Direction> getLongClick() {
        return onLongClickSubject.asObservable();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
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

    @Override
    public int getItemViewType(int position) {
        return directions.get(position) != null ? ITEM_VIEW_TYPE_BASIC : ITEM_VIEW_TYPE_FOOTER ;
    }

    public void addItems(@NonNull List<Direction> newDataSetItems) {
        directions.addAll(newDataSetItems);
        notifyDataSetChanged();
    }

    public void removeItem(Direction item) {
        int indexOfItem = directions.indexOf(item);
        if (indexOfItem != -1) {
            this.directions.remove(indexOfItem);
            notifyItemRemoved(indexOfItem);
        }
    }
}
