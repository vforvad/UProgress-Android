package com.example.vsokoltsov.uprogress.presenters.directions;

import android.support.v4.app.Fragment;

import com.example.vsokoltsov.uprogress.ui.ApplicationBaseActivity;

/**
 * Created by vsokoltsov on 29.12.16.
 */

public interface DirectionsListPresenter {
    void onCreate(ApplicationBaseActivity activity);
    void loadDirections();
}
