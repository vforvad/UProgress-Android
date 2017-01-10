package com.example.vsokoltsov.uprogress.user.presenters;

import com.example.vsokoltsov.uprogress.user.current.UserRequest;

/**
 * Created by vsokoltsov on 10.01.17.
 */

public interface UserProfilePresenter {
    void updateUser(
            String userId,
            UserRequest request
    );
}
