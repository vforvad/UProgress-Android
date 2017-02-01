package com.vsokoltsov.uprogress.directions_list.presenters;

import android.support.v7.widget.RecyclerView;

import com.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.vsokoltsov.uprogress.directions_list.models.DirectionRequest;

/**
 * Created by vsokoltsov on 29.12.16.
 */

public interface DirectionsListPresenter {
    void loadDirections();
    void refreshList();
    void loadMoreDirections();
    void createDirection(String userId, DirectionRequest directionRequest);
    void updateDirection(int userId, int directionId, DirectionRequest directionRequest);
    void deleteDirection(int userId, int directionId);
}
