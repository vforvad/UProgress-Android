package com.vsokoltsov.uprogress.user.views;

import com.vsokoltsov.uprogress.user.current.User;

/**
 * Created by vsokoltsov on 10.01.17.
 */

public interface UserProfileView {
    void successUpdate(User user);
    void failedUpdate(Throwable t);
    void startLoader();
    void stopLoader();
}
