package com.example.vsokoltsov.uprogress.direction_detail.model.steps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    @JsonProperty("updated_at")
    private Date updatedAt;

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

    public void setUpdatedAt(String updatedAt) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(updatedAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.updatedAt = date;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
