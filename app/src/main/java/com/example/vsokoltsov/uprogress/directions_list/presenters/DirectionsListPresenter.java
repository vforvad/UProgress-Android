package com.example.vsokoltsov.uprogress.directions_list.presenters;

import android.support.v7.widget.RecyclerView;

import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;

/**
 * Created by vsokoltsov on 29.12.16.
 */

public interface DirectionsListPresenter {
    void onCreate(ApplicationBaseActivity activity);
    void loadDirections();
    void refreshList();
    void scrollDownListener(RecyclerView recyclerView, int dx, int dy);
}
