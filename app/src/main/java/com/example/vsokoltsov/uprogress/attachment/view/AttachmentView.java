package com.example.vsokoltsov.uprogress.attachment.view;

import com.example.vsokoltsov.uprogress.authentication.models.Attachment;

/**
 * Created by vsokoltsov on 16.01.17.
 */

public interface AttachmentView {
    void successUpload(Attachment attachment);
    void failedUpload(Throwable t);
}
