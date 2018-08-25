package com.huzzey.weather2.data;

import android.support.annotation.VisibleForTesting;


import com.huzzey.weather2.data.service.ApiService;
import com.huzzey.weather2.datatype.Weather;
import com.huzzey.weather2.datatype.WeatherItems;
import com.huzzey.weather2.datatype.WeatherResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by darren.huzzey on 08/09/2016.
 */

public class InMemoryRepository implements Repository {
    private ApiService service;

    @VisibleForTesting List<Weather> cachedWeather;

    public InMemoryRepository(ApiService apiService) {
        service = apiService;
    }

    @Override
    public Single<List<Weather>> getWeatherData(Integer countryID) {
        if(cachedWeather == null) {
            return service.getWeather(countryID).subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                .map(new Function<WeatherResponse, List<Weather>>() {
                    @Override
                    public List<Weather> apply(WeatherResponse weatherResponse) throws Exception {
                        List<Weather> l = new ArrayList<>();
                        Weather weather;
                        String currentDate = "";
                        for(WeatherItems items : weatherResponse.getList()) {
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
                        return cachedWeather;
                    }
                });
        } else {
            return Single.just(cachedWeather);
        }
    }

    @Override
    public void clearCache() {
        cachedWeather = null;
    }
}
