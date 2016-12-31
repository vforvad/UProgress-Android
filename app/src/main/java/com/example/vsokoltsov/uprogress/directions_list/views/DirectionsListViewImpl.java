package com.example.vsokoltsov.uprogress.directions_list.views;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.vsokoltsov.uprogress.directions_list.models.Direction;
import com.example.vsokoltsov.uprogress.directions_list.models.DirectionsList;
import com.example.vsokoltsov.uprogress.directions_list.DirectionListViewHolder;

import java.io.IOException;

/**
 * Created by vsokoltsov on 29.12.16.
 */

public class DirectionsListViewImpl implements DirectionsListView {
    public final DirectionListViewHolder viewHolder;

    public DirectionsListViewImpl(DirectionListViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }

    @Override
    public void successResponse(DirectionsList list) {
        for(int i = 0; i < list.getDirections().size(); i++) {
            Direction d = (Direction) list.getDirections().get(i);
            viewHolder.adapter.directions.add(d);
        }
        viewHolder.adapter.notifyDataSetChanged();
    }

    @Override
    public void failedResponse(Throwable t) throws IOException {

    }

    @Override
    public void refreshList(DirectionsList list) {
        viewHolder.adapter.directions = list.getDirections();
        viewHolder.adapter.notifyDataSetChanged();
        viewHolder.swipeLayout.setRefreshing(false);
    }

    @Override
    public SwipeRefreshLayout getRefreshLayout() {
        return viewHolder.swipeLayout;
    }
}
