package com.vsokoltsov.uprogress.user.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.TextView;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.direction_detail.model.steps.Step;
import com.vsokoltsov.uprogress.user.current.UserItem;

/**
 * Created by vsokoltsov on 08.01.17.
 */

public class UserInfoItem extends CardView {
    private TextView title;
    private TextView value;
    public UserInfoItem(Context context) {
        super(context);
        init();
    }

    public UserInfoItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UserInfoItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.user_item, this);
        title = (TextView) findViewById(R.id.infoTitle);
        value = (TextView) findViewById(R.id.infoValue);
    }

    public void bind(UserItem item) {
        title.setText(item.getTitle());
        value.setText(item.getDescription());
    }
}
