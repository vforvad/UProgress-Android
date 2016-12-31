package com.example.vsokoltsov.uprogress.authentication.views;

import com.example.vsokoltsov.uprogress.authentication.messages.UserMessage;
import com.example.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.example.vsokoltsov.uprogress.authentication.models.CurrentUser;

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
