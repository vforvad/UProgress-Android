package com.example.vsokoltsov.uprogress.common;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vsokoltsov.uprogress.R;

/**
 * Created by vsokoltsov on 28.01.17.
 */

public class ErrorDialog {
    private Dialog dialog;
    private Activity activity;
    private ImageView errorImage;

    public ErrorDialog() {}

    public ErrorDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void show( String msg){
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.error_dialog);
        Drawable errorImg = ContextCompat.getDrawable(activity, R.drawable.ic_error);
        errorImage = (ImageView) dialog.findViewById(R.id.errorImage);
        errorImage.setImageDrawable(errorImg);
        errorImage.setScaleType(ImageView.ScaleType.FIT_CENTER);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
