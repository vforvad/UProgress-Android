package com.example.vsokoltsov.uprogress.authentication.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 23.11.16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Attachment {
    @JsonProperty("attachable_type")
    private String attachableType;
    @JsonProperty("attachable_id")
    private String attachableId;
    @JsonProperty("id")
    private int id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("url")
    private String url;

    public String getAttachableType() {
        return attachableType;
    }

    public void setAttachableType(String attachableType) {
        this.attachableType = attachableType;
    }

    public String getAttachableId() {
        return attachableId;
    }

    public void setAttachableId(String attachableId) {
        this.attachableId = attachableId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
