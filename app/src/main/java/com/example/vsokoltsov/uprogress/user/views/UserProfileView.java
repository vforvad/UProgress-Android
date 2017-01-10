package com.example.vsokoltsov.uprogress.user.views;

import com.example.vsokoltsov.uprogress.user.current.User;

/**
 * Created by vsokoltsov on 10.01.17.
 */

public interface UserProfileView {
    void successUpdate(User user);
    void failedUpdate(Throwable t);
}
