package com.example.vsokoltsov.uprogress.authentication.views;

import com.example.vsokoltsov.uprogress.authentication.models.CurrentUser;
import com.example.vsokoltsov.uprogress.common.BaseView;

/**
 * Created by vsokoltsov on 24.12.16.
 */

public interface AuthorizationScreen extends BaseView {
    void successResponse(CurrentUser currentUser);
    void failedResponse(Throwable t);
}
