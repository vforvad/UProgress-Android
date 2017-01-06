package com.example.vsokoltsov.uprogress.directions_list.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 06.01.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectionData {
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;

    public DirectionData(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
