package com.example.vsokoltsov.uprogress.attachment.network;

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
    Observable<ResponseBody> upload(@Part("description") RequestBody description,
                                    @Part MultipartBody.Part file,
                                    @Part("attachable_type") String attachableType);
}
