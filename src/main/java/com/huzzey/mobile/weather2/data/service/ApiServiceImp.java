package com.huzzey.mobile.weather2.data.service;

import com.huzzey.mobile.weather2.BuildConfig;
import com.huzzey.mobile.weather2.datatype.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by darren.huzzey on 08/09/2016.
 */

public class ApiServiceImp implements ApiService {
    private static Retrofit retrofit;

    public ApiServiceImp() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    @Override
    public void getWeather(final GetWeatherCallBack callBack, Integer countryId) {
        retrofit.create(WeatherAPI.class).loadWeather(countryId, BuildConfig.APP_KEY)
                .enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                callBack.onSuccess(response.body().getList());
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }


}
