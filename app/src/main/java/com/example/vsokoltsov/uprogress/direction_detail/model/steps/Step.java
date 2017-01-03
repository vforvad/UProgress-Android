package com.example.vsokoltsov.uprogress.direction_detail.model.steps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 03.01.17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Step {
    @JsonProperty("id")
    private int id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("is_done")
    private Boolean isDone;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getChecked() {
        if (isDone == null) {
            return false;
        }
        return isDone;
    }

    public void setChecked(Boolean checked) {
        this.isDone = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
