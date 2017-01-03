package com.example.vsokoltsov.uprogress.direction_detail.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.common.adapters.BaseListAdapter;
import com.example.vsokoltsov.uprogress.common.adapters.BaseListAdapterInterface;
import com.example.vsokoltsov.uprogress.direction_detail.model.steps.Step;
import com.example.vsokoltsov.uprogress.direction_detail.ui.StepsItem;
import com.example.vsokoltsov.uprogress.direction_detail.view.DirectionDetailListAdapter;
import com.example.vsokoltsov.uprogress.directions_list.adapters.DirectionsListAdapter;
import com.example.vsokoltsov.uprogress.directions_list.models.Direction;

import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by vsokoltsov on 03.01.17.
 */

public class StepsListAdapter extends BaseListAdapter {
    DirectionDetailListAdapter view;

    public StepsListAdapter(List list, RecyclerView recyclerView, DirectionDetailListAdapter view) {
        super(list, recyclerView, view);
        this.view = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_list_item, parent, false);
        StepViewHolder viewHolder = new StepViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, int position) {
        Step step = (Step) items.get(position);
        StepViewHolder viewHolder = ((StepViewHolder) holder);
        CheckBox checkBox = viewHolder.stepsItem.getCheckbox();
        viewHolder.bind(step);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = checkBox.isChecked();
                view.onCheckboxChanged(step, isChecked);
            }
        });
    }

    public class StepViewHolder extends RecyclerView.ViewHolder {
        private StepsItem stepsItem;

        public StepViewHolder (View itemView) {
            super(itemView);
            stepsItem = (StepsItem) itemView;
        }

        public void bind(Step step) {
            stepsItem.bind(step);
        }

    }
}
