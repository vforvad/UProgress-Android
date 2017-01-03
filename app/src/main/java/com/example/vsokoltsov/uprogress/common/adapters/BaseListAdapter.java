package com.example.vsokoltsov.uprogress.common.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.directions_list.DirectionsListAdapter;
import com.example.vsokoltsov.uprogress.directions_list.models.Direction;
import com.example.vsokoltsov.uprogress.directions_list.views.DirectionsListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 03.01.17.
 */

public abstract class BaseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public BaseListAdapterInterface baseListAdapterInterface;
    private RecyclerView recyclerView;
    public List list;

    private final int ITEM_VIEW_TYPE_BASIC = 0;
    private final int ITEM_VIEW_TYPE_FOOTER = 1;

    public abstract RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType);
    public abstract void onBindBaseViewHolder(RecyclerView.ViewHolder holder, int position);

    public BaseListAdapter(List list, RecyclerView recyclerView,
                                 final BaseListAdapterInterface view) {
            this.list = list;
            this.recyclerView = recyclerView;
            this.baseListAdapterInterface = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateBaseViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    private RecyclerView.ViewHolder onCreateFooterView(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.progress_bar, parent, false);
        return new FooterViewHolder(v);
    }

    private void onBindFooterView(RecyclerView.ViewHolder genericHolder, int position) {
        ((FooterViewHolder) genericHolder).progressBar.setIndeterminate(true);
    }


    public <T> List<T> magicalGetter() {
        return new ArrayList<T>();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) != null ? ITEM_VIEW_TYPE_BASIC : ITEM_VIEW_TYPE_FOOTER ;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }
}
