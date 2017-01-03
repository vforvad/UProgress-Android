package com.example.vsokoltsov.uprogress.direction_detail.model;

import com.example.vsokoltsov.uprogress.directions_list.models.Direction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 03.01.17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectionDetail {
    @JsonProperty("direction")
    private Direction direction;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
