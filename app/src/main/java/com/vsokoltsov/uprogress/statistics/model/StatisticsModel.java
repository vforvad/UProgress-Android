package com.vsokoltsov.uprogress.statistics.model;

import rx.Observable;

/**
 * Created by vsokoltsov on 18.01.17.
 */

public interface StatisticsModel {
    Observable<StatisticsResponse> getStatistics(String userID);
}
