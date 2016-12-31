package com.example.vsokoltsov.uprogress.views;

import com.example.vsokoltsov.uprogress.messages.UserMessage;
import com.example.vsokoltsov.uprogress.models.authorization.AuthorizationService;
import com.example.vsokoltsov.uprogress.models.authorization.CurrentUser;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by vsokoltsov on 26.12.16.
 */

abstract class AuthView {
    AuthorizationService auth = AuthorizationService.getInstance();

    void setCurrentUser(CurrentUser currentUser) {
        auth.setCurrentUser(currentUser.getUser());
        EventBus.getDefault().post(new UserMessage("currentUser", currentUser.getUser()));
    }
}
