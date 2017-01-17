package com.example.vsokoltsov.uprogress.common;

import android.util.DisplayMetrics;
import android.view.Window;

/**
 * Created by vsokoltsov on 07.01.17.
 */
public class ScreenSizeHelper {
    private DisplayMetrics dm;

    public ScreenSizeHelper(Window window) {
        dm = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    public int getScreenHeight() {
        return dm.heightPixels;
    }

    public int getScreenWidth() {
        return dm.widthPixels;
    }
}
