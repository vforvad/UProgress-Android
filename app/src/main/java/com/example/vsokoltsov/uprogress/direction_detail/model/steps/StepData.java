package com.example.vsokoltsov.uprogress.direction_detail.model.steps;

/**
 * Created by vsokoltsov on 03.01.17.
 */

public class StepData {
    final String title;
    final String description;
    final boolean isDone;

    public StepData(String title, String description, boolean isDone) {
        this.title = title;
        this.description = description;
        this.isDone = isDone;
    }
}
