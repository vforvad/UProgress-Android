package com.example.vsokoltsov.uprogress.user.current;

/**
 * Created by vsokoltsov on 31.12.16.
 */

public interface CurrentUserView {
    void currentUserReceived(CurrentUser currentUser);
    void currentUserFailedToReceive(Throwable t);
}
