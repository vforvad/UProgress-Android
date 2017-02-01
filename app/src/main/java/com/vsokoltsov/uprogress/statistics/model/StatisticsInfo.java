package com.vsokoltsov.uprogress.statistics.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by vsokoltsov on 18.01.17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticsInfo {
    @JsonProperty("directions")
    private List<StatisticsItem> directions;
    @JsonProperty("steps")
    private List<StatisticsItem> steps;
    @JsonProperty("directions_steps")
    private List<StatisticsItem> directionsSteps;


    public List<StatisticsItem> getDirections() {
        return directions;
    }

    public void setDirections(List<StatisticsItem> directions) {
        this.directions = directions;
    }

    public List<StatisticsItem> getSteps() {
        return steps;
    }

    public void setSteps(List<StatisticsItem> steps) {
        this.steps = steps;
    }

    public List<StatisticsItem> getDirectionsSteps() {
        return directionsSteps;
    }

    public void setDirectionsSteps(List<StatisticsItem> directionsSteps) {
        this.directionsSteps = directionsSteps;
    }
}
