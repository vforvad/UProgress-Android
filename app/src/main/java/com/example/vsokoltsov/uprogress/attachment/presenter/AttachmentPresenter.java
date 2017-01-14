package com.example.vsokoltsov.uprogress.attachment.presenter;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;

/**
 * Created by vsokoltsov on 15.01.17.
 */

public interface AttachmentPresenter {
    void uploadImage(RequestBody description,
                     MultipartBody.Part file,
                     String attachableType);
}
