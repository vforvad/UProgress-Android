package com.example.vsokoltsov.uprogress.direction_detail.view;

import com.example.vsokoltsov.uprogress.direction_detail.model.steps.Step;
import com.example.vsokoltsov.uprogress.directions_list.models.Direction;

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

}
