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

public class DirectionViewHolder extends RecyclerView.ViewHolder  {
    CardView cv;
    DirectionsListAdapter adapter;
    public TextView directionTitle;
    public TextView directionResultValue;
    private Direction direction;

    public DirectionViewHolder(View itemView) {
        super(itemView);
    }

    public DirectionViewHolder(View itemView, DirectionsListAdapter adapter) {
        super(itemView);
        this.adapter = adapter;
        directionTitle= (TextView) itemView.findViewById(R.id.directionTitle);

    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
