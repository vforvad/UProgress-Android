package com.example.vsokoltsov.uprogress.launch;

import com.example.vsokoltsov.uprogress.user.current.CurrentUser;
import com.example.vsokoltsov.uprogress.user.current.CurrentUserModel;
import com.example.vsokoltsov.uprogress.user.current.CurrentUserView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 31.12.16.
 */

public class LaunchPresenter {
    private CurrentUserView view;
    private CurrentUserModel model;

    public LaunchPresenter(CurrentUserView view, CurrentUserModel model) {
        this.view = view;
        this.model = model;
    }

    public void getCurrentUser() {
        model.getCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CurrentUser>() {
                    @Override
                    public void onCompleted() {
                        view.completedCurrentUserRequest();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.currentUserFailedToReceive(e);
                        view.completedCurrentUserRequest();
                    }

                    @Override
                    public void onNext(CurrentUser user) {
                        view.currentUserReceived(user);
                    }
                });
    }
}
