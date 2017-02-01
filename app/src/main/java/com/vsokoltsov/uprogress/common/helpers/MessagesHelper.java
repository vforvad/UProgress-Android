package com.vsokoltsov.uprogress.common.helpers;

import android.content.Context;
import android.content.res.Resources;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.direction_detail.model.steps.Step;

/**
 * Created by vsokoltsov on 04.01.17.
 */

public class MessagesHelper {
    private Resources resources;

    public MessagesHelper(Resources resources) {
        this.resources = resources;
    }

    public String messageForStepUpdate(Step step) {
        String finalString;
        String value;

        if (step.getChecked()) {
            value = resources.getString(R.string.step_update_true);
        }
        else {
            value = resources.getString(R.string.step_update_false);
        }

        finalString = String.format(resources.getString(R.string.step_success_update_message), value);
        return finalString;
    }
}
