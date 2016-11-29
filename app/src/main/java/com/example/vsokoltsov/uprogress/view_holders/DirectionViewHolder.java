package com.example.vsokoltsov.uprogress.view_holders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.adapters.DirectionsListAdapter;
import com.example.vsokoltsov.uprogress.models.directions.Direction;

/**
 * Created by vsokoltsov on 27.11.16.
 */

public class DirectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    CardView cv;
    DirectionsListAdapter adapter;
    public TextView directionTitle;
    public TextView directionPercents;
    private Direction direction;

    public DirectionViewHolder(View itemView) {
        super(itemView);
    }

    public DirectionViewHolder(View itemView, DirectionsListAdapter adapter) {
        super(itemView);
        this.adapter = adapter;
        cv = (CardView) itemView.findViewById(R.id.directionItem);
        directionTitle= (TextView) itemView.findViewById(R.id.directionTitle);
        directionPercents = (TextView)itemView.findViewById(R.id.directionPercents);

        cv.setOnClickListener(this);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void onClick(View v) {

    }
}
