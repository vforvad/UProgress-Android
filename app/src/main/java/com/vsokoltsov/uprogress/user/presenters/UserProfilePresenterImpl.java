package com.vsokoltsov.uprogress.user.presenters;

import com.vsokoltsov.uprogress.directions_list.models.DirectionResponse;
import com.vsokoltsov.uprogress.user.current.UserRequest;
import com.vsokoltsov.uprogress.user.current.UserResponse;
import com.vsokoltsov.uprogress.user.models.UserModel;
import com.vsokoltsov.uprogress.user.views.UserProfileView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 10.01.17.
 */

public class UserProfilePresenterImpl implements UserProfilePresenter {
    final UserProfileView view;
    final UserModel model;

    public UserProfilePresenterImpl(UserProfileView view, UserModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void updateUser(String userId, UserRequest request) {
        view.startLoader();
        model.updateUser(userId, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserResponse>() {
                    @Override
                    public void onCompleted() {
                        view.stopLoader();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.failedUpdate(e);
                        view.stopLoader();
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        view.successUpdate(userResponse.getUser());
                    }
                });
        ;
    }
}
