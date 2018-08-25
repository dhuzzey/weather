package com.huzzey.weather2.data;


import com.huzzey.weather2.datatype.Weather;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by darren.huzzey on 08/09/2016.
 */

public interface Repository {

    Single<List<Weather>> getWeatherData(Integer countryID);

    void clearCache();
}
