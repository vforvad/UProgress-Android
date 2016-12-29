package com.example.vsokoltsov.uprogress.views.directions;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.vsokoltsov.uprogress.adapters.DirectionsListAdapter;
import com.example.vsokoltsov.uprogress.models.directions.DirectionsList;

import java.io.IOException;

/**
 * Created by vsokoltsov on 29.12.16.
 */

public interface DirectionsListView {
    void successResponse(DirectionsList list);
    void failedResponse(Throwable t) throws IOException;
    void refreshList(DirectionsList list);
    SwipeRefreshLayout getRefreshLayout();
}
