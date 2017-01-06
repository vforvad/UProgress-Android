package com.example.vsokoltsov.uprogress.directions_list.models;

import rx.Observable;

/**
 * Created by vsokoltsov on 29.12.16.
 */

public interface DirectionModel {
    Observable<DirectionsList> getDirectionsList(String userId, int pageNumber);
    Observable<DirectionResponse> createDirection(String userId, DirectionRequest directionRequest);
}
