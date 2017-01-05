package com.example.vsokoltsov.uprogress.direction_detail.view;

import com.example.vsokoltsov.uprogress.direction_detail.model.steps.Step;
import com.example.vsokoltsov.uprogress.direction_detail.model.steps.StepsList;
import com.example.vsokoltsov.uprogress.directions_list.models.Direction;

import java.util.List;

/**
 * Created by vsokoltsov on 03.01.17.
 */

public interface DirectionDetailView {
    void successResponse(Direction direction);
    void failureResponse(Throwable t);
    void startLoader();
    void stopLoader();
    void successStepUpdate(Step step);
    void failureStepUpdate(Throwable t);
    void onStartRefresh();
    void onStopRefresh();
    void successDelete(Step step, int[] positions);
    void failedDelete(Throwable t);
    void successStepCreate(Step step);
    void failedStepCreate(Throwable t);
}
