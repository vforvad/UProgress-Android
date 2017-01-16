package com.example.vsokoltsov.uprogress.attachment.presenter;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Part;

/**
 * Created by vsokoltsov on 15.01.17.
 */

public interface AttachmentPresenter {
    void uploadImage(MultipartBody.Part file,
                     RequestBody attachableType,
                     RequestBody attachableId);
}
