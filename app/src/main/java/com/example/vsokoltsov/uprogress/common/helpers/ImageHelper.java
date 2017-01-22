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

import java.io.File;

/**
 * Created by vsokoltsov on 08.01.17.
 */
public class ImageHelper {
    private Context context;
    Drawable error;
    Picasso picasso;

    public ImageHelper(Context context) {
        this.context = context;
//        error = ContextCompat.getDrawable(this.context, R.drawable.error);
        picasso = new Picasso.Builder(context)
                .downloader(getOkHttpLoader())
                .build();
    }

    public void load(String url, ImageView destination, int emptyImage) {
        picasso
                .load(url)
                .fit()
                .into(destination);
    }

    public void load(File file, ImageView destination, int emptyImage) {
        picasso
                .load(file)
                .fit()
                .into(destination);
    }

    public void setEmptyImage(ImageView destination, int emptyImage) {
        Drawable drawable = ContextCompat.getDrawable(context, emptyImage);
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


    private OkHttp3Downloader getOkHttpLoader() {
        okhttp3.OkHttpClient okHttp3Client = new okhttp3.OkHttpClient();
        OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(okHttp3Client);
        return okHttp3Downloader;
    }
}
