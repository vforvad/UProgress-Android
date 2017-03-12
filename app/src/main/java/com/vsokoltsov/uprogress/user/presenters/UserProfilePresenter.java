package com.vsokoltsov.uprogress.user.presenters;

import com.vsokoltsov.uprogress.user.current.UserRequest;

/**
 * Created by vsokoltsov on 10.01.17.
 */

public interface UserProfilePresenter {
    void updateUser(
            String userId,
            UserRequest request
    );
}
