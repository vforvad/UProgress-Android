package com.example.vsokoltsov.uprogress.adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.models.directions.Direction;
import com.example.vsokoltsov.uprogress.view_holders.DirectionViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 27.11.16.
 */

public class DirectionsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<Direction> directions = new ArrayList<Direction>();
    private Activity activity;
    public Fragment fragment;

    public DirectionsListAdapter(List<Direction> directions, Activity activity) {
        this.directions = directions;
        this.activity = activity;
    }

    public DirectionsListAdapter(List<Direction> directions, Fragment fragment) {
        this.directions = directions;
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.direction_list_item, parent, false);
        DirectionViewHolder viewHolder = new DirectionViewHolder(v, this);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DirectionViewHolder directionViewHolder = (DirectionViewHolder) holder;
        Direction direction = directions.get(position);

        directionViewHolder.setDirection(direction);
        directionViewHolder.directionTitle.setText(direction.getTitle());
        directionViewHolder.directionPercents.setText(String.format("%d", direction.getPercentsResult()));

//        if (course.getImage() != null) {
//            String fullURL = course.getImage().getUrl();
//            Picasso.with(this.activity.getApplicationContext())
//                    .load(fullURL)
//                    .placeholder(emptyUser)
//                    .into(courseViewHolder.courseImage);
//        }
//        else {
//            courseViewHolder.courseImage.setImageDrawable(emptyUser);
//        }
    }


    @Override
    public int getItemCount() {
        return directions.size();
    }
}
