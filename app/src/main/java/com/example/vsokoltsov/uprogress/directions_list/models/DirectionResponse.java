package com.example.vsokoltsov.uprogress.directions_list.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 06.01.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectionResponse {
    @JsonProperty("direction")
    private Direction direction;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
