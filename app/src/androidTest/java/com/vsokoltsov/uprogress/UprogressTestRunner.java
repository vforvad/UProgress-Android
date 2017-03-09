package com.vsokoltsov.uprogress;

import android.app.Application;
import android.support.test.runner.AndroidJUnitRunner;

import okhttp3.mockwebserver.MockWebServer;

/**
 * Created by vsokoltsov on 09.03.17.
 */

public class UprogressTestRunner extends AndroidJUnitRunner {
    @Override
    public void callApplicationOnCreate(Application app) {
        MockWebServer server = new MockWebServer();
        super.callApplicationOnCreate(app);
    }
}
