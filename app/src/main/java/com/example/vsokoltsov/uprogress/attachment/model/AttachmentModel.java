package com.example.vsokoltsov.uprogress.attachment.model;

import com.example.vsokoltsov.uprogress.authentication.models.Attachment;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by vsokoltsov on 15.01.17.
 */

public interface AttachmentModel {
    Observable<AttachmentResponse> uploadImage(MultipartBody.Part file,
                                         RequestBody attachableType,
                                         RequestBody attachableId);
}
