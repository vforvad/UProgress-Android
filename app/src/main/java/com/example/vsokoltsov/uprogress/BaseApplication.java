package com.example.vsokoltsov.uprogress;

import android.app.Application;
import android.content.Context;

/**
 * Created by vsokoltsov on 25.12.16.
 */

public class BaseApplication extends Application {

    private Context context;
    public static String NAME = "uprogress";

    @Override
    public void onCreate() {
        super.onCreate();
    }


}
