package com.vsokoltsov.uprogress.attachment.network;

import com.vsokoltsov.uprogress.attachment.model.AttachmentResponse;
import com.vsokoltsov.uprogress.authentication.models.Attachment;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by vsokoltsov on 15.01.17.
 */

public interface AttachmentApi {
    @Multipart
    @POST("attachments")
    Observable<AttachmentResponse> upload(@Part MultipartBody.Part file,
                                          @Part("attachable_type") RequestBody attachableType,
                                          @Part("attachable_id") RequestBody attachableId);
}
