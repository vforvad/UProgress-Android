package com.example.vsokoltsov.uprogress.directions_list;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.example.vsokoltsov.uprogress.directions_list.models.Direction;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 29.12.16.
 */

public class DirectionListViewHolder {
    public final SwipeRefreshLayout swipeLayout;
    private final RecyclerView rv;
    private final LinearLayoutManager llm;

    private List<Direction> directions = new ArrayList<Direction>();
    public DirectionsListAdapter adapter;

    public DirectionListViewHolder(SwipeRefreshLayout swipeLayout,
                                   RecyclerView rv,
                                   LinearLayoutManager llm,
                                   Fragment fragment) {
        this.swipeLayout = swipeLayout;
        this.rv = rv;
        adapter = new DirectionsListAdapter(directions, fragment);
        this.llm = llm;

        rv.setHasFixedSize(true);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
    }
}
