package com.vsokoltsov.uprogress.reset_password.model;

import com.vsokoltsov.uprogress.direction_detail.model.DirectionDetail;

import rx.Observable;

/**
 * Created by vsokoltsov on 07.04.17.
 */

public interface ResetPasswordModel {
    Observable<ResetPasswordResponse> resetPassword(ResetPasswordRequest request);
}
