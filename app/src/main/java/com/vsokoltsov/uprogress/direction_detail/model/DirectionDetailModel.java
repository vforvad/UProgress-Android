package com.vsokoltsov.uprogress.direction_detail.model;

import com.vsokoltsov.uprogress.direction_detail.model.steps.StepRequest;
import com.vsokoltsov.uprogress.direction_detail.model.steps.StepResponse;
import com.vsokoltsov.uprogress.direction_detail.model.steps.StepsList;
import com.vsokoltsov.uprogress.directions_list.models.Direction;

import rx.Observable;
import rx.Observer;

/**
 * Created by vsokoltsov on 03.01.17.
 */

public interface DirectionDetailModel {
    Observable<DirectionDetail> loadDirection(String userNick, String directionId);
    Observable<StepResponse> updateStep(
            String userNick,
            String directionId,
            String stepId,
            StepRequest request
    );
    Observable<StepsList> loadSteps(
            String userNick,
            String directionId,
            int pageNumber
    );
    Observable<StepResponse> deleteStep(
            String userNick,
            String directionId,
            String stepId
    );
    Observable<StepResponse> createStep(
            String userNick,
            String directionId,
            StepRequest request
    );
}
