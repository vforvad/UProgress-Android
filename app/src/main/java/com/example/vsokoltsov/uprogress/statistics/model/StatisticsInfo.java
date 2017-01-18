package com.example.vsokoltsov.uprogress.statistics.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 18.01.17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticsInfo {
    @JsonProperty("directions")
    private StatisticsScope directions;
    @JsonProperty("steps")
    private StatisticsScope steps;
    @JsonProperty("directions_steps")
    private StatisticsScope directionsSteps;

    public void setDirections(StatisticsScope directions) {
        this.directions = directions;
    }

    public StatisticsScope getDirections() {
        return directions;
    }

    public StatisticsScope getSteps() {
        return steps;
    }

    public void setSteps(StatisticsScope steps) {
        this.steps = steps;
    }

    public void setDirectionsSteps(StatisticsScope directionsSteps) {
        this.directionsSteps = directionsSteps;
    }

    public StatisticsScope getDirectionsSteps() {
        return directionsSteps;
    }
}
