package com.vsokoltsov.uprogress.reset_password.view;

import com.vsokoltsov.uprogress.common.BaseView;

/**
 * Created by vsokoltsov on 07.04.17.
 */

public interface ResetPasswordView extends BaseView {
    void successReset(String message);
    void failedReset(Throwable t);
}
