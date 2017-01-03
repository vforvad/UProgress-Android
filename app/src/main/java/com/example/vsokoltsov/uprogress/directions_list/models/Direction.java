package com.example.vsokoltsov.uprogress.directions_list.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Created by vsokoltsov on 27.11.16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("direction")
public class Direction implements Parcelable {
    @JsonProperty("id")
    private int id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("percents_result")
    private int percentsResult;
    @JsonProperty("steps_count")
    private int stepsCount;
    @JsonProperty("finished_steps_count")
    private int finishedStepsCount;

    public Direction() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPercentsResult(int percentsResult) {
        this.percentsResult = percentsResult;
    }

    public int getPercentsResult() {
        return percentsResult;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(percentsResult);
    }

    public static final Parcelable.Creator<Direction> CREATOR = new Parcelable.Creator<Direction>() {
        public Direction createFromParcel(Parcel in) {
            Direction direction = new Direction();
            direction.id = in.readInt();
            direction.percentsResult = in.readInt();
            direction.title = in.readString();
            return direction;
        }
        public Direction[] newArray(int size) {
            return new Direction[size];
        }
    };

    public int getStepsCount() {
        return stepsCount;
    }

    public void setStepsCount(int stepsCount) {
        this.stepsCount = stepsCount;
    }

    public void setFinishedStepsCount(int finishedStepsCount) {
        this.finishedStepsCount = finishedStepsCount;
    }

    public int getFinishedStepsCount() {
        return finishedStepsCount;
    }

    public String getFinishedStepsRation() {
        return new StringBuilder().append(finishedStepsCount).append("/").append(stepsCount).toString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
