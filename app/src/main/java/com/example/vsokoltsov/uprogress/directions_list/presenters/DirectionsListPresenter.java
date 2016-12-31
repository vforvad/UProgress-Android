package com.example.vsokoltsov.uprogress.directions_list.presenters;

import android.support.v7.widget.RecyclerView;

import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;

/**
 * Created by vsokoltsov on 29.12.16.
 */

public interface DirectionsListPresenter {
    void loadDirections();
    void refreshList();
    void loadMoreDirections();
}
