package com.example.vsokoltsov.uprogress.views.directions;

import com.example.vsokoltsov.uprogress.adapters.DirectionsListAdapter;
import com.example.vsokoltsov.uprogress.models.directions.Direction;
import com.example.vsokoltsov.uprogress.models.directions.DirectionsList;
import com.example.vsokoltsov.uprogress.view_holders.DirectionListViewHolder;

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
}
