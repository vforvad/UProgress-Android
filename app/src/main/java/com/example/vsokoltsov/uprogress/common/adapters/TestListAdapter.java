package com.example.vsokoltsov.uprogress.common.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.example.vsokoltsov.uprogress.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 07.01.17.
 */

public class TestListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<String> items;
    private Activity activity;

    private final int VISIBLE_THRESHOLD = 5;

    private final int ITEM_VIEW_TYPE_BASIC = 0;
    private final int ITEM_VIEW_TYPE_FOOTER = 1;

    private int firstVisibleItem, visibleItemCount, totalItemCount, previousTotal = 0;
    private boolean loading = true;


    public TestListAdapter(List<String> items, Activity activity){
        this.items = items;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_VIEW_TYPE_BASIC;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item, parent, false);
            QuestionsViewHolder qvh = new QuestionsViewHolder(v);
            return qvh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_VIEW_TYPE_BASIC) {
            QuestionsViewHolder questionHolder = (QuestionsViewHolder) holder;
            questionHolder.testText.setText(items.get(position));
        }
        else {
//            ((ProgressViewHolder) holder).progressBar.;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class QuestionsViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView testText;

        private int questionId;

        QuestionsViewHolder(View itemView) {
            super(itemView);
            testText =  (TextView) itemView.findViewById(R.id.testText);

        }


    }
}
