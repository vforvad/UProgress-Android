package com.example.vsokoltsov.uprogress.attachment.model;

import com.example.vsokoltsov.uprogress.attachment.network.AttachmentApi;
import com.example.vsokoltsov.uprogress.common.utils.ApiRequester;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

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
    public void uploadImage(RequestBody description, MultipartBody.Part file, String attachableType) {
        service.upload(description, file, attachableType);
    }
}
