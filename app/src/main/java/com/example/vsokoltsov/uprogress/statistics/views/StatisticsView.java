package com.example.vsokoltsov.uprogress.statistics.views;

import com.example.vsokoltsov.uprogress.statistics.model.StatisticsInfo;

/**
 * Created by vsokoltsov on 18.01.17.
 */

public interface StatisticsView {
    void successLoadStatistics(StatisticsInfo statisticsInfo);
    void failedLoadStatistics(Throwable t);
}
