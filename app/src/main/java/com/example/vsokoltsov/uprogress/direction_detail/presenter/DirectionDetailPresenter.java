package com.example.vsokoltsov.uprogress.direction_detail.presenter;

import com.example.vsokoltsov.uprogress.direction_detail.model.steps.StepRequest;

/**
 * Created by vsokoltsov on 03.01.17.
 */

public interface DirectionDetailPresenter {
    void loadDirection(String userNick, String directionId);
    void updateStep(
            String userNick,
            String directionId,
            String stepId,
            StepRequest request
    );

    void reloadDirection(String userNick, String directionId);
    void deleteStep(String userNick, String directionId, String stepId, int[] positions);
}
