package com.example.vsokoltsov.uprogress.direction_detail.view;

import com.example.vsokoltsov.uprogress.common.adapters.BaseListAdapterInterface;
import com.example.vsokoltsov.uprogress.direction_detail.model.steps.Step;

/**
 * Created by vsokoltsov on 03.01.17.
 */

public interface DirectionDetailListAdapter extends BaseListAdapterInterface {
    void onCheckboxChanged(Step step, boolean value);
}
