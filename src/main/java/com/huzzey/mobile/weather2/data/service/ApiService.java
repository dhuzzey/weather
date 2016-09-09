package com.huzzey.mobile.weather2.data.service;

import com.huzzey.mobile.weather2.datatype.WeatherItems;

import java.util.List;

/**
 * Created by darren.huzzey on 08/09/2016.
 */

public interface ApiService {

    interface GetWeatherCallBack {
        void onSuccess(List<WeatherItems> list);
        void onError(String error);
    }

    void getWeather(GetWeatherCallBack callBack, Integer countryId);
}
