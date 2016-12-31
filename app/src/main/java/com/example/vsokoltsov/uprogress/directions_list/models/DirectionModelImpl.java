package com.example.vsokoltsov.uprogress.directions_list.models;

import android.content.Context;

import com.example.vsokoltsov.uprogress.directions_list.network.DirectionsApi;
import com.example.vsokoltsov.uprogress.common.helpers.PreferencesHelper;
import com.example.vsokoltsov.uprogress.common.utils.ApiRequester;
import com.example.vsokoltsov.uprogress.directions_list.DirectionListViewHolder;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by vsokoltsov on 29.12.16.
 */

public class DirectionModelImpl implements DirectionModel {
    private final DirectionListViewHolder viewHolder;

    public DirectionModelImpl(DirectionListViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }

    @Override
    public Observable<DirectionsList> getDirectionsList(String userId, int pageNumber) {
        Context context = viewHolder.swipeLayout.getContext(); //TODO:get the context with a better mecanism than this
        PreferencesHelper helper = new PreferencesHelper(context);
        //TODO: Put retrofit and rest adapter creation in one place
        Retrofit retrofit = ApiRequester.getInstance().getRestAdapter();
        DirectionsApi service = retrofit.create(DirectionsApi.class);
        return service.getDirections(userId, pageNumber);
    }
}
