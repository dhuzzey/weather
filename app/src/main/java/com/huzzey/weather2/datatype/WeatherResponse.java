package com.huzzey.weather2.datatype;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by darren.huzzey on 08/09/2016.
 */

public class WeatherResponse {
    @SerializedName("list")
    List<WeatherItems> list;

    public List<WeatherItems> getList(){
        return list;
    }
}
