package com.vsokoltsov.uprogress.attachment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import okhttp3.MultipartBody;

/**
 * Created by vsokoltsov on 16.01.17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class AttachmentRequest {
    @JsonProperty("file")
    private MultipartBody.Part file;
    private String attachableId;
    private String attachableType;
}
