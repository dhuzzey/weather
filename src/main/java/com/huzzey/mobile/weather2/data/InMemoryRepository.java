package com.huzzey.mobile.weather2.data;

import android.support.annotation.VisibleForTesting;

import com.huzzey.mobile.weather2.data.service.ApiService;
import com.huzzey.mobile.weather2.datatype.Weather;
import com.huzzey.mobile.weather2.datatype.WeatherItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darren.huzzey on 08/09/2016.
 */

public class InMemoryRepository implements Repository {
    private ApiService service;

    @VisibleForTesting
    List<Weather> cachedWeather;

    public InMemoryRepository(ApiService apiService) {
        service = apiService;
    }

    @Override
    public void getWeatherData(final GetWeatherCallback callback, Integer countryID) {
        if(cachedWeather == null) {
            service.getWeather(new ApiService.GetWeatherCallBack() {
                @Override
                public void onSuccess(List<WeatherItems> list) {
                    List<Weather> l = new ArrayList<>();
                    Weather weather;
                    String currentDate = "";
                    for(WeatherItems items : list) {
                        if (!items.getText().substring(0, 10).equals(currentDate)) {
                            weather = new Weather();
                            weather.setHeader(true);
                            weather.setDateTime(items.getText());
                            currentDate = items.getText().substring(0, 10);
                            l.add(weather);
                        }
                        weather = new Weather();
                        weather.setHeader(false);
                        weather.setDescription(items.getWeatherList().get(0).getDescription());
                        weather.setIcon(items.getWeatherList().get(0).getIcon());
                        weather.setDateTime(items.getText());
                        l.add(weather);
                    }
                    cachedWeather = l;
                    callback.onSuccess(cachedWeather);
                }

                @Override
                public void onError(String error) {
                    callback.onFailure(error);
                }
            }, countryID);
        } else {
            callback.onSuccess(cachedWeather);
        }
    }

    @Override
    public void clearCache() {
        cachedWeather = null;
    }
}
