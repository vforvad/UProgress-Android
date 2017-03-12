package com.vsokoltsov.uprogress.common.helpers;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by vsokoltsov on 17.01.17.
 */

public interface UploadHelper {
    void setUploadFileData(MultipartBody.Part body, RequestBody attachmentableId, RequestBody attachmentableType);
}
