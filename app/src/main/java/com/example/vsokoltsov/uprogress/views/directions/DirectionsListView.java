package com.example.vsokoltsov.uprogress.views.directions;

import com.example.vsokoltsov.uprogress.models.directions.DirectionsList;

import java.io.IOException;

/**
 * Created by vsokoltsov on 29.12.16.
 */

public interface DirectionsListView {
    void successResponse(DirectionsList list);
    void failedResponse(Throwable t) throws IOException;
}
