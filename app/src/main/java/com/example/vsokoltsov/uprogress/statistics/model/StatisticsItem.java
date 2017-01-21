package com.example.vsokoltsov.uprogress.statistics.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.vsokoltsov.uprogress.directions_list.models.Direction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vsokoltsov on 18.01.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticsItem implements Parcelable {
    @JsonProperty("label")
    private String label;
    @JsonProperty("value")
    private Double value;
    @JsonProperty("color")
    private String color;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(value);
        dest.writeString(label);
    }

    public static final Parcelable.Creator<StatisticsItem> CREATOR = new Parcelable.Creator<StatisticsItem>() {
        public StatisticsItem createFromParcel(Parcel in) {
            StatisticsItem item = new StatisticsItem();
            item.value = in.readDouble();
            item.label = in.readString();
            return item;
        }
        public StatisticsItem[] newArray(int size) {
            return new StatisticsItem[size];
        }
    };
}
