package com.example.vsokoltsov.uprogress.views.authorization;

import com.example.vsokoltsov.uprogress.models.authorization.CurrentUser;
import com.example.vsokoltsov.uprogress.models.authorization.Token;
import com.example.vsokoltsov.uprogress.views.BaseView;

import java.io.IOException;

/**
 * Created by vsokoltsov on 24.12.16.
 */

public interface AuthorizationScreen extends BaseView {
    void successResponse(CurrentUser currentUser);
    void failedResponse(Throwable t);
}
