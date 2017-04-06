package com.vsokoltsov.uprogress.authentication.views;

import com.vsokoltsov.uprogress.common.BaseView;

/**
 * Created by vsokoltsov on 07.04.17.
 */

public interface RestorePasswordScreen extends BaseView {
    void successRestoreResponse(String token);
    void failedRestoreResponse(Throwable t);
}
