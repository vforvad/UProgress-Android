package com.vsokoltsov.uprogress.common.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.widget.ImageView;

import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;
import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.common.BaseApplication;
import com.vsokoltsov.uprogress.common.CircleTransform;
import com.vsokoltsov.uprogress.user.current.User;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by vsokoltsov on 08.01.17.
 */
public class ImageHelper {
    private Context context;
    Drawable error;
    Picasso picasso;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public ImageHelper(Context context) {
        this.context = context;
//        error = ContextCompat.getDrawable(this.context, R.drawable.error);
        picasso = new Picasso.Builder(context)
                .downloader(getOkHttpLoader())
                .build();
    }

    public void load(String url, ImageView destination, int emptyImage) {
        RequestCreator requestCreator = picasso.load(url).fit();

        if (context.getResources().getBoolean(R.bool.isTablet)) {
            requestCreator.transform(new CircleTransform());
        }
        requestCreator.into(destination);
    }

    public void load(File file, ImageView destination, int emptyImage) {
        picasso
                .load(file)
                .fit()
                .into(destination);
    }

    public void setEmptyImage(Context context, ImageView destination, int emptyImage) {
            Drawable drawable = context.getResources().getDrawable(emptyImage);
            picasso
                    .load(emptyImage)
                    .fit()
                    .error(drawable)
                    .into(destination);


    }

    public void setUserImage(Context context, User user, ImageView destination, int emptyImage) {
        if (user.getImage() != null) {
            load(user.getImage().getUrl(), destination, emptyImage);
        }
        else {
            setEmptyImage(context, destination, emptyImage);
        }
    }


    private OkHttp3Downloader getOkHttpLoader() {
        okhttp3.OkHttpClient okHttp3Client = new okhttp3.OkHttpClient();
        OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(okHttp3Client);
        return okHttp3Downloader;
    }
}
