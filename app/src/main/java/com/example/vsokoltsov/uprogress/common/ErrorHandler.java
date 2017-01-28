package com.example.vsokoltsov.uprogress.common;

import android.app.Activity;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.common.utils.RetrofitException;

/**
 * Created by vsokoltsov on 28.01.17.
 */

public class ErrorHandler {
    private ErrorDialog dialog;
    final private Activity activity;

    public ErrorHandler(Activity activity) {
        this.activity = activity;
        dialog = new ErrorDialog(this.activity);
    }

    public void showMessage(Throwable throwable) {
        RetrofitException error = (RetrofitException) throwable;
        int errorCode = error.getResponse().code();

        String message;
        if (errorCode >= 500) {
            message = activity.getResources().getString(R.string.internal_server_error);
            dialog.show(message);
        }
    }
}
