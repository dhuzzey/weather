package com.huzzey.mobile.weather2.data;

import com.huzzey.mobile.weather2.datatype.Weather;

import java.util.List;

/**
 * Created by darren.huzzey on 08/09/2016.
 */

public interface Repository {
    interface GetWeatherCallback{
        void onSuccess(List<Weather> list);
        void onFailure(String error);
    }

    void getWeatherData(GetWeatherCallback callback, Integer countryID);

    void clearCache();
}
