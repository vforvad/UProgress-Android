package com.example.vsokoltsov.uprogress.directions_list.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 06.01.17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectionRequest {
    @JsonProperty("direction")
    private DirectionData directionData;

    public DirectionRequest() {
    }

    public DirectionRequest(String title, String description) {
        this.directionData = new DirectionData(title, description);
    }

    public DirectionData getDirectionData() {
        return directionData;
    }

    public void setDirectionData(DirectionData directionData) {
        this.directionData = directionData;
    }
}
