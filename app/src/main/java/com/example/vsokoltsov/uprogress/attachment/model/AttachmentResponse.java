package com.example.vsokoltsov.uprogress.attachment.model;

import com.example.vsokoltsov.uprogress.authentication.models.Attachment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 17.01.17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class AttachmentResponse {
    @JsonProperty("attachment")
    private Attachment attachment;

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public Attachment getAttachment() {
        return attachment;
    }
}
