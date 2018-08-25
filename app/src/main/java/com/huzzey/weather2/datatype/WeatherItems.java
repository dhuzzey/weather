package com.huzzey.weather2.datatype;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by darren.huzzey on 08/09/2016.
 */

public class WeatherItems {
    @SerializedName("weather")
    private List<WeatherDetail> weatherList;
    @SerializedName("dt_txt")
    private String text;

    public String getText() {
        return text;
    }

    public List<WeatherDetail> getWeatherList() {
        return weatherList;
    }
}
