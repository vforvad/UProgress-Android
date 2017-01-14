package com.example.vsokoltsov.uprogress.attachment.model;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by vsokoltsov on 15.01.17.
 */

public interface AttachmentModel {
    void uploadImage(RequestBody description,
                     MultipartBody.Part file,
                     String attachableType);
}
