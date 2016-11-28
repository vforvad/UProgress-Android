package com.example.vsokoltsov.uprogress.models.directions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 27.11.16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Direction {
    @JsonProperty("id")
    private int id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("percents_result")
    private int percentsResult;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPercentsResult(int percentsResult) {
        this.percentsResult = percentsResult;
    }

    public int getPercentsResult() {
        return percentsResult;
    }
}
