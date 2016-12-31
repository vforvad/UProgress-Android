package com.example.vsokoltsov.uprogress.user.current;

import rx.Observable;

/**
 * Created by vsokoltsov on 31.12.16.
 */

public interface CurrentUserModel {
    Observable<CurrentUser> getCurrentUser();
}
