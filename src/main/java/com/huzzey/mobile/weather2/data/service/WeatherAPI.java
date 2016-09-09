package com.huzzey.mobile.weather2.data.service;

import com.huzzey.mobile.weather2.datatype.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by darren.huzzey on 07/06/16.
 */
public interface WeatherAPI {
    @Headers({"Content-type: application/json"})
    @GET("/data/2.5/forecast")
    Call<WeatherResponse> loadWeather(@Query("id") Integer id, @Query("APPID") String appId);
}
