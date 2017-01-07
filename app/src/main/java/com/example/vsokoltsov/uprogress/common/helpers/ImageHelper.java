package com.example.vsokoltsov.uprogress.common.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.example.vsokoltsov.uprogress.R;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by vsokoltsov on 08.01.17.
 */
public class ImageHelper {
    private static Context context_;
    private static ImageHelper ourInstance = new ImageHelper();
    okhttp3.OkHttpClient okHttp3Client = new okhttp3.OkHttpClient();
    OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(okHttp3Client);

    public static ImageHelper getInstance(Context context) {
        context_ = context;
        return ourInstance;
    }

    private ImageHelper() {
    }

    public void load(String url, ImageView destination, Drawable emptyImage) {
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                // loading of the bitmap was a success
                // TODO do some action with the bitmap
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                // loading of the bitmap failed
                // TODO do some action/warning/error message
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        Drawable error = ContextCompat.getDrawable(context_, R.drawable.error);
        Picasso picasso = new Picasso.Builder(context_)
                .downloader(okHttp3Downloader)
                .build();
        picasso
                .load(url)
                .fit()
                .error(error)
                .placeholder(emptyImage)
                .into(destination);
    }
}
