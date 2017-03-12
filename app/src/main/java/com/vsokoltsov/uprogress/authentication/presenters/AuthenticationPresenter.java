package com.vsokoltsov.uprogress.authentication.presenters;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.vsokoltsov.uprogress.authentication.models.SignIn.SignInRequest;
import com.vsokoltsov.uprogress.authentication.models.SignUp.SignUpRequest;

/**
 * Created by vsokoltsov on 24.12.16.
 */

public interface AuthenticationPresenter {
    //void onCreate(Fragment fragment);
    void onSignInSubmit(SignInRequest request);
    void onSignUpSubmit(SignUpRequest request);
}
