package com.example.vsokoltsov.uprogress.attachment.model;

import com.example.vsokoltsov.uprogress.attachment.network.AttachmentApi;
import com.example.vsokoltsov.uprogress.authentication.models.Attachment;
import com.example.vsokoltsov.uprogress.common.utils.ApiRequester;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by vsokoltsov on 15.01.17.
 */

public class AttachmentModelImpl implements AttachmentModel {
    private AttachmentApi service;

    public AttachmentModelImpl() {
        Retrofit retrofit = ApiRequester.getInstance().getRestAdapter();
        service = retrofit.create(AttachmentApi.class);

    }

    @Override
    public Observable<AttachmentResponse> uploadImage(MultipartBody.Part file, RequestBody attachableType, RequestBody attachableId) {
        return service.upload(file, attachableType, attachableId);
    }
}
