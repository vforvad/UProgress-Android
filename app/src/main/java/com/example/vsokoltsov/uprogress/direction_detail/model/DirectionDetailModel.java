package com.example.vsokoltsov.uprogress.direction_detail.model;

import com.example.vsokoltsov.uprogress.directions_list.models.Direction;

import rx.Observable;
import rx.Observer;

/**
 * Created by vsokoltsov on 03.01.17.
 */

public interface DirectionDetailModel {
    Observable<DirectionDetail> loadDirection(String userNick, String directionId);
}
