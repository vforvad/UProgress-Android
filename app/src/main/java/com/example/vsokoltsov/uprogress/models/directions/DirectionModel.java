package com.example.vsokoltsov.uprogress.models.directions;

import rx.Observable;

/**
 * Created by vsokoltsov on 29.12.16.
 */

public interface DirectionModel {
    Observable<DirectionsList> getDirectionsList(String userId, int pageNumber);
}
