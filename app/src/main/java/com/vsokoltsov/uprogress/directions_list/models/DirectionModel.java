package com.vsokoltsov.uprogress.directions_list.models;

import rx.Observable;

/**
 * Created by vsokoltsov on 29.12.16.
 */

public interface DirectionModel {
    Observable<DirectionsList> getDirectionsList(String userId, int pageNumber);
    Observable<DirectionResponse> createDirection(String userId, DirectionRequest directionRequest);
    Observable<DirectionResponse> updateDirection(int userId, int directionId, DirectionRequest request);
    Observable<DirectionResponse> deleteDirection(int userId, int directionId);
}
