package com.example.vsokoltsov.uprogress.models.directions;

import com.example.vsokoltsov.uprogress.api.DirectionsApi;
import com.example.vsokoltsov.uprogress.api.UserApi;
import com.example.vsokoltsov.uprogress.utils.ApiRequester;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by vsokoltsov on 29.12.16.
 */

public class DirectionModelImpl implements DirectionModel {
    @Override
    public Observable<DirectionsList> getDirectionsList(String userId, int pageNumber) {
        Retrofit retrofit = ApiRequester.getInstance().getRestAdapter();
        DirectionsApi service = retrofit.create(DirectionsApi.class);
        return service.getDirections(userId, pageNumber);
    }
}
