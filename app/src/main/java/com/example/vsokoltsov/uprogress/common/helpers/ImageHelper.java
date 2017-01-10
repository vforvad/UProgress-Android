package com.example.vsokoltsov.uprogress.common.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.user.current.User;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by vsokoltsov on 08.01.17.
 */
public class ImageHelper {
    private static Context context_;
    static Drawable error;
    static Picasso picasso;
    private static ImageHelper ourInstance = new ImageHelper();


    public static ImageHelper getInstance(Context context) {
        context_ = context;
        error = ContextCompat.getDrawable(context_, R.drawable.error);
        setPicasoInstance();
        return ourInstance;
    }

    private ImageHelper() {
    }

    public void load(String url, ImageView destination, int emptyImage) {
        picasso
                .load(url)
                .fit()
                .error(error)
                .placeholder(emptyImage)
                .into(destination);
    }

    public void setEmptyImage(ImageView destination, int emptyImage) {
        Drawable drawable = ContextCompat.getDrawable(context_, emptyImage);
        picasso
                .load(emptyImage)
                .fit()
                .error(drawable)
                .into(destination);

    }

    public void setUserImage(User user, ImageView destination, int emptyImage) {
        if (user.getImage() != null) {
            load(user.getImage().getUrl(), destination, emptyImage);
        }
        else {
            setEmptyImage(destination, emptyImage);
        }
    }

    private static void setPicasoInstance() {
        picasso = new Picasso.Builder(context_)
                .downloader(getOkHttpLoader())
                .build();
    }

    private static OkHttp3Downloader getOkHttpLoader() {
        okhttp3.OkHttpClient okHttp3Client = new okhttp3.OkHttpClient();
        OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(okHttp3Client);
        return okHttp3Downloader;
    }
}
