package com.example.vsokoltsov.uprogress.directions_list.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.directions_list.models.Direction;

/**
 * Created by vsokoltsov on 24.12.16.
 */

public class DirectionCompletionItemView extends CardView {
    private StringBuilder builder;
    private TextView directionPercents;
    private TextView directionTitle;
    private TextView directionRation;
    private ImageView stepsIcon;
    private TextView directionUpdatedAt;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public DirectionCompletionItemView(Context context) {
        super(context);
        init();
    }

    public DirectionCompletionItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DirectionCompletionItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.direction_list_item, this);

        directionPercents = (TextView) findViewById(R.id.directionPercents);
        directionTitle = (TextView) findViewById(R.id.directionTitle);
        directionRation = (TextView) findViewById(R.id.directionRation);
        stepsIcon = (ImageView) findViewById(R.id.stepsIcon);
        directionUpdatedAt = (TextView) findViewById(R.id.directionUpdatedAt);
    }

    public void bind(Direction direction) {
        directionTitle.setText(direction.getTitle());
        directionPercents.setText(Integer.toString(direction.getPercentsResult()));
        directionRation.setText(direction.getFinishedStepsRation());

        //Icon
        stepsIcon.setImageResource(R.drawable.steps_icon);

        if (direction.getUpdatedAt() != null) {
            long now = System.currentTimeMillis();
            String date = (String) DateUtils.getRelativeTimeSpanString(direction.getUpdatedAt().getTime(), now, DateUtils.DAY_IN_MILLIS);
            directionUpdatedAt.setText(date);
        }
    }
}
