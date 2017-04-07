package com.vsokoltsov.uprogress.reset_password.presenter;

import com.vsokoltsov.uprogress.direction_detail.model.DirectionDetailModel;
import com.vsokoltsov.uprogress.direction_detail.model.steps.StepResponse;
import com.vsokoltsov.uprogress.direction_detail.view.DirectionDetailView;
import com.vsokoltsov.uprogress.reset_password.model.ResetPasswordModel;
import com.vsokoltsov.uprogress.reset_password.model.ResetPasswordRequest;
import com.vsokoltsov.uprogress.reset_password.model.ResetPasswordResponse;
import com.vsokoltsov.uprogress.reset_password.view.ResetPasswordView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 07.04.17.
 */

public class ResetPasswordPresenterImpl implements ResetPasswordPresenter {
    ResetPasswordModel model;
    ResetPasswordView screen;

    public ResetPasswordPresenterImpl(ResetPasswordModel model, ResetPasswordView screen) {
        this.model = model;
        this.screen = screen;
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        screen.startLoader();
        model.resetPassword(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResetPasswordResponse>() {
                    @Override
                    public void onCompleted() {
                        screen.stopLoader();
                    }

                    @Override
                    public void onError(Throwable e) {
                        screen.failedReset(e);
                        screen.stopLoader();
                    }

                    @Override
                    public void onNext(ResetPasswordResponse response) {
                        screen.successReset(response.getMessage());
                    }
                });
    }
}
