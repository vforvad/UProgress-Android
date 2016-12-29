package com.example.vsokoltsov.uprogress.view_holders;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.example.vsokoltsov.uprogress.adapters.DirectionsListAdapter;

import org.solovyev.android.views.llm.LinearLayoutManager;

/**
 * Created by vsokoltsov on 29.12.16.
 */

public class DirectionListViewHolder {
    private final SwipeRefreshLayout swipeLayout;
    private final RecyclerView rv;
    private final DirectionsListAdapter adapter;
    private final LinearLayoutManager llm;

    public DirectionListViewHolder(SwipeRefreshLayout swipeLayout, RecyclerView rv,
                                   DirectionsListAdapter adapter, LinearLayoutManager llm) {
        this.swipeLayout = swipeLayout;
        this.rv = rv;
        this.adapter = adapter;
        this.llm = llm;

        rv.setHasFixedSize(true);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
    }
}
