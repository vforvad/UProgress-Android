package com.example.vsokoltsov.uprogress.direction_detail.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.vsokoltsov.uprogress.common.adapters.BaseListAdapter;
import com.example.vsokoltsov.uprogress.common.adapters.BaseListAdapterInterface;

import java.util.List;

/**
 * Created by vsokoltsov on 03.01.17.
 */

public class StepsListAdapter extends BaseListAdapter {
    public StepsListAdapter(List list, RecyclerView recyclerView, BaseListAdapterInterface view) {
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
