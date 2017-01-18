package com.example.vsokoltsov.uprogress.statistics.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 18.01.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticsItem {
    @JsonProperty("label")
    private String label;
    @JsonProperty("value")
    private Double value;
    @JsonProperty("color")
    private String color;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
