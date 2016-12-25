package com.example.vsokoltsov.uprogress.presenters;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by vsokoltsov on 24.12.16.
 */

public interface AuthenticationPresenter {
    void onCreate(Fragment fragment);
    void onSignInSubmit();
}
