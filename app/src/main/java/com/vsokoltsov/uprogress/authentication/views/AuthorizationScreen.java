package com.vsokoltsov.uprogress.authentication.views;

import com.vsokoltsov.uprogress.user.current.CurrentUser;
import com.vsokoltsov.uprogress.common.BaseView;

/**
 * Created by vsokoltsov on 24.12.16.
 */

public interface AuthorizationScreen extends BaseView {
    void successResponse(CurrentUser currentUser);
    void failedResponse(Throwable t);
}
