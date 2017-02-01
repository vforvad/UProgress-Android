package com.vsokoltsov.uprogress.attachment.presenter;

import com.vsokoltsov.uprogress.attachment.model.AttachmentModel;
import com.vsokoltsov.uprogress.attachment.model.AttachmentResponse;
import com.vsokoltsov.uprogress.attachment.view.AttachmentView;
import com.vsokoltsov.uprogress.authentication.models.Attachment;
import com.vsokoltsov.uprogress.user.current.CurrentUser;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 15.01.17.
 */

public class AttachmentPresenterImpl implements AttachmentPresenter {
    private final AttachmentModel model;
    private final AttachmentView view;

    public AttachmentPresenterImpl(AttachmentModel model, AttachmentView view) {
        this.model = model;
        this.view = view;
    }


    @Override
    public void uploadImage(MultipartBody.Part file, RequestBody attachableType, RequestBody attachableId) {
        view.startLoader();
        model.uploadImage(file, attachableType, attachableId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AttachmentResponse>() {
                    @Override
                    public void onCompleted() {
                        view.stopLoader();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.stopLoader();
                        view.failedUpload(e);
                    }

                    @Override
                    public void onNext(AttachmentResponse attachmentResponse) {
                        // TODO Add redirect to user's profile
                        view.successUpload(attachmentResponse.getAttachment());
                    }
                });
    }
}
