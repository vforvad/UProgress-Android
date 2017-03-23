package com.vsokoltsov.uprogress.common;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.vsokoltsov.uprogress.common.utils.DeviceTokenManager;

/**
 * Created by vsokoltsov on 12.03.17.
 */

public class UProgressFirebaseService extends FirebaseInstanceIdService {

    private static final String TAG = UProgressFirebaseService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        DeviceTokenManager.getInstance().setToken(refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(refreshedToken);
    }
}
