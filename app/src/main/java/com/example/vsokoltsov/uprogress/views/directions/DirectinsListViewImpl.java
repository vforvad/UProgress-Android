package com.example.vsokoltsov.uprogress.views.directions;

import com.example.vsokoltsov.uprogress.models.directions.DirectionsList;
import com.example.vsokoltsov.uprogress.view_holders.DirectionListViewHolder;

import java.io.IOException;

/**
 * Created by vsokoltsov on 29.12.16.
 */

public class DirectinsListViewImpl implements DirectionsListView {
    public DirectionListViewHolder viewHolder;

    @Override
    public void successResponse(DirectionsList list) {

    }

    @Override
    public void failedResponse(Throwable t) throws IOException {

    }
}
