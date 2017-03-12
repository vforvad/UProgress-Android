package com.vsokoltsov.uprogress.common.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;

import com.vsokoltsov.uprogress.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 03.01.17.
 */

public abstract class BaseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    public BaseListAdapterInterface baseListAdapterInterface;
    private RecyclerView recyclerView;
    public List items;
    public List orig;

    private final int ITEM_VIEW_TYPE_BASIC = 0;
    private final int ITEM_VIEW_TYPE_FOOTER = 1;

    private int firstVisibleItem, visibleItemCount, totalItemCount, previousTotal = 0;

    public abstract RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType);
    public abstract void onBindBaseViewHolder(RecyclerView.ViewHolder holder, int position);

    public BaseListAdapter(List list, RecyclerView recyclerView,
                                 final BaseListAdapterInterface view) {
            this.items = list;
            this.recyclerView = recyclerView;
            this.baseListAdapterInterface = view;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    totalItemCount = linearLayoutManager.getItemCount();
                    visibleItemCount = linearLayoutManager.getChildCount();
                    firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                    int firstNumber = totalItemCount - visibleItemCount - 1;
                    if (firstNumber > 0 && (firstNumber <= firstVisibleItem)) {
                        // End has been reached

                        if (view != null) {
                            baseListAdapterInterface.loadMore();
                        }
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_BASIC) {
            return onCreateBaseViewHolder(parent, viewType);
        }
        else if (viewType == ITEM_VIEW_TYPE_FOOTER) {
            return onCreateFooterView(parent, viewType);
        }
        else {
            throw new IllegalStateException("Invalid type, this type ot items " + viewType + " can't be handled");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_VIEW_TYPE_BASIC) {
            onBindBaseViewHolder(holder, position);
        } else {
            onBindFooterView(holder, position);
        }
    }

    private RecyclerView.ViewHolder onCreateFooterView(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.progress_bar, parent, false);
        return new FooterViewHolder(v);
    }

    private void onBindFooterView(RecyclerView.ViewHolder genericHolder, int position) {
        ((FooterViewHolder) genericHolder).progressBar.setIndeterminate(true);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) != null ? ITEM_VIEW_TYPE_BASIC : ITEM_VIEW_TYPE_FOOTER ;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }


    public void addDirection(Object item) {
        if (!items.contains(item)) {
            items.add(item );
            notifyItemInserted(items.size() - 1);
        }
    }

    public void removeItem(Object item) {
        int indexOfItem = items.indexOf(item);
        if (indexOfItem != -1) {
            this.items.remove(indexOfItem);
            notifyItemRemoved(indexOfItem);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List results = new ArrayList();
                if (orig == null)
                    orig = items;
                if (constraint != null) {
                    if (orig != null & orig.size() > 0) {
                        for (final Object obj : orig) {
                            if (baseListAdapterInterface.getSearchAttribute(obj).toLowerCase().contains(constraint.toString().toLowerCase()))
                                results.add(obj);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                items = (ArrayList) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
