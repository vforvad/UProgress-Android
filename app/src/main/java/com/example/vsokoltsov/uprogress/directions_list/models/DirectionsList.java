package com.example.vsokoltsov.uprogress.directions_list.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by vsokoltsov on 27.11.16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectionsList {
    @JsonProperty("directions")
    private List<Direction> directions;

    public List<Direction> getDirections() {
        return directions;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }
}
