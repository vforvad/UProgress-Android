package com.vsokoltsov.uprogress.common;

/**
 * Created by vsokoltsov on 09.03.17.
 */

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

public class UProgressTestRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        return super.newApplication(cl, BaseTestApplication.class.getName(), context);
    }
}
