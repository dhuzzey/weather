package com.huzzey.weather2.datatype;

import com.google.gson.annotations.SerializedName;

/**
 * Created by darren.huzzey on 08/09/2016.
 */

public class WeatherDetail {
    @SerializedName("description")
    String description;
    @SerializedName("icon")
    String icon;

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
