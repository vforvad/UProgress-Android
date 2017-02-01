package com.vsokoltsov.uprogress.directions_list.views;

import android.support.v4.widget.SwipeRefreshLayout;

import com.vsokoltsov.uprogress.common.BaseView;
import com.vsokoltsov.uprogress.directions_list.models.Direction;
import com.vsokoltsov.uprogress.directions_list.models.DirectionsList;

import java.io.IOException;

/**
 * Created by vsokoltsov on 29.12.16.
 */

public interface DirectionsListView extends BaseView {
    void successResponse(DirectionsList list);
    void failedResponse(Throwable t) throws IOException;
    void refreshList(DirectionsList list);
    void startRefreshing();
    void stopRefreshing();
    void startFooterLoader();
    void stopFooterLoader();
    void successDirectionCreation(Direction direction);
    void failedDirectionCreation(Throwable t);
    void successUpdateDirection(Direction direction);
    void failedUpdateDirection(Throwable t);
    void successDeleteDirection(Direction direction);
    void failedDeleteDirection(Throwable t);
}
