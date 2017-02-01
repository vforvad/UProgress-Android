package com.vsokoltsov.uprogress.common.utils;

import android.content.Context;

/**
 * Created by vsokoltsov on 31.12.16.
 */
public class ContextManager {
    private static ContextManager ourInstance = new ContextManager();
    private Context context;

    public static ContextManager getInstance() {
        return ourInstance;
    }

    private ContextManager() {
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
