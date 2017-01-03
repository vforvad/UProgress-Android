package com.example.vsokoltsov.uprogress.direction_detail.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.direction_detail.model.steps.Step;

import org.w3c.dom.Text;

/**
 * Created by vsokoltsov on 03.01.17.
 */

public class StepsItem extends CardView {
    private TextView stepsTitle;
    private CheckBox checkBox;

    public StepsItem(Context context) {
        super(context);
        init();
    }

    public StepsItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StepsItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.steps_item, this);
        stepsTitle = (TextView) findViewById(R.id.stepsTitle);
        checkBox = (CheckBox) findViewById(R.id.isChecked);
    }

    public void bind(Step step) {
        stepsTitle.setText(step.getTitle());
        checkBox.setChecked(step.getChecked());
    }
}
