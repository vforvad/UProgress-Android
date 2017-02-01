package com.vsokoltsov.uprogress.statistics.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 18.01.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticsScope {
    List<StatisticsItem> items;

    public List<StatisticsItem> getItems() {
        return this.items;
    }

    public void setItems(List<StatisticsItem> statistics) {
        this.items = statistics;
    }
}
