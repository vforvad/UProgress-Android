package com.example.vsokoltsov.uprogress.view_holders;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.adapters.DirectionsListAdapter;
import com.example.vsokoltsov.uprogress.interfaces.DirectionItemClickListener;
import com.example.vsokoltsov.uprogress.models.directions.Direction;

import org.w3c.dom.Text;

/**
 * Created by vsokoltsov on 27.11.16.
 */

public class DirectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    CardView cv;
    DirectionsListAdapter adapter;
    public TextView directionTitle;
    public TextView directionPercents;
    private Direction direction;
    private DirectionItemClickListener callbacks;
    private ImageView stepsIcon;
    public TextView directionRation;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public DirectionViewHolder(View itemView) {
        super(itemView);
    }

    public DirectionViewHolder(View itemView, DirectionsListAdapter adapter) {
        super(itemView);
        this.adapter = adapter;
        callbacks = (DirectionItemClickListener) adapter.fragment;
        cv = (CardView) itemView.findViewById(R.id.directionItem);
        directionTitle= (TextView) itemView.findViewById(R.id.directionTitle);
        directionPercents = (TextView)itemView.findViewById(R.id.directionPercents);
        directionRation = (TextView) itemView.findViewById(R.id.directionRation);
        Drawable stepsIconImg = AppCompatResources.getDrawable(adapter.fragment.getActivity().getApplicationContext(), R.drawable.steps_icon);
//        Drawable stepsIconImg = adapter.fragment.getActivity().getApplicationContext().getResources().getDrawable( R.drawable.steps);
        stepsIcon = (ImageView) itemView.findViewById(R.id.stepsIcon);
        stepsIcon.setImageDrawable(stepsIconImg);
        cv.setOnClickListener(this);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void onClick(View v) {
        callbacks.onItemClicked(this.adapter.directions.get(getAdapterPosition()));
    }
}
