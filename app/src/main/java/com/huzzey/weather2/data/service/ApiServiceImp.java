package com.huzzey.weather2.data.service;


import com.huzzey.weather2.BuildConfig;
import com.huzzey.weather2.datatype.WeatherResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by darren.huzzey on 08/09/2016.
 */

public class ApiServiceImp implements ApiService {
    private static Retrofit retrofit;

    public ApiServiceImp() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
    }

    @Override
    public Single<WeatherResponse> getWeather(Integer countryId) {
        return retrofit.create(WeatherAPI.class).loadWeather(countryId, BuildConfig.APP_KEY)
                .subscribeOn(Schedulers.io());
    }


}
