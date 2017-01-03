package com.example.vsokoltsov.uprogress.direction_detail.model.steps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 03.01.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StepData {
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("is_done")
    private boolean isDone;

    public StepData(String title, String description, boolean isDone) {
        this.title = title;
        this.description = description;
        this.isDone = isDone;
    }
}
