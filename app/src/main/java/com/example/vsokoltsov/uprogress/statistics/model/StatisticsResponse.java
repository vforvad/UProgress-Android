package com.example.vsokoltsov.uprogress.statistics.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 18.01.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticsResponse {
    @JsonProperty("statistics")
    private StatisticsInfo statistics;

    public void setStatistics(StatisticsInfo statistics) {
        this.statistics = statistics;
    }

    public StatisticsInfo getStatistics() {
        return statistics;
    }
}
