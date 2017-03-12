package com.vsokoltsov.uprogress.direction_detail.model.steps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 03.01.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StepRequest {
    @JsonProperty("step")
    private StepData step;

    public StepRequest(String title, String description, boolean isDone) {
        this.step = new StepData(title, description, isDone);
    }
}
