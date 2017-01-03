package com.example.vsokoltsov.uprogress.directions_list.adapters;

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
import com.example.vsokoltsov.uprogress.common.adapters.BaseListAdapter;
import com.example.vsokoltsov.uprogress.common.adapters.BaseListAdapterInterface;
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

public class DirectionsListAdapter extends BaseListAdapter {
    public Fragment fragment;

    private final PublishSubject<Direction> onClickSubject = PublishSubject.create();
    private final PublishSubject<Direction> onLongClickSubject = PublishSubject.create();

    public DirectionsListAdapter(List<Direction> directions, RecyclerView recyclerView,
                     final BaseListAdapterInterface view){
        super(directions, recyclerView, view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.direction_completion_view_item, parent, false);
        DirectionViewHolder viewHolder = new DirectionViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, int position) {
        Direction direction = (Direction) items.get(position);

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

    public Observable<Direction> getPositionClicks(){
        return onClickSubject.asObservable();
    }

    public Observable<Direction> getLongClick() {
        return onLongClickSubject.asObservable();
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
}
