package com.huzzey.weather2.data.service;

import com.huzzey.weather2.datatype.WeatherItems;
import com.huzzey.weather2.datatype.WeatherResponse;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by darren.huzzey on 08/09/2016.
 */

public interface ApiService {
    Single<WeatherResponse> getWeather(Integer countryId);
}
