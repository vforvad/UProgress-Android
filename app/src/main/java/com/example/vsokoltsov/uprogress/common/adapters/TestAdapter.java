package com.example.vsokoltsov.uprogress.common.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.vsokoltsov.uprogress.directions_list.models.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 03.01.17.
 */

public class TestAdapter extends BaseListAdapter {
    public TestAdapter(List list, RecyclerView recyclerView, BaseListAdapterInterface view) {
        super(list, recyclerView, view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
