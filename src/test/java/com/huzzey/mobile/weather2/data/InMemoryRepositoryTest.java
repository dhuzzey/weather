package com.huzzey.mobile.weather2.data;

import com.huzzey.mobile.weather2.data.service.ApiService;
import com.huzzey.mobile.weather2.datatype.Weather;
import com.huzzey.mobile.weather2.datatype.WeatherItems;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by darren.huzzey on 09/09/2016.
 */
public class InMemoryRepositoryTest {
    private InMemoryRepository repository;

    @Mock
    private Repository.GetWeatherCallback getWeatherCallback;

    @Mock
    private ApiService service;

    @Captor
    private ArgumentCaptor<ApiService.GetWeatherCallBack> callBack;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        repository = new InMemoryRepository(service);
    }

    @Test
    public void testCache() throws Exception {
        assertTrue(repository.cachedWeather == null);

        repository.getWeatherData(getWeatherCallback, 524901);
        verify(service).getWeather(callBack.capture(), eq(524901));

        List<WeatherItems> list = new ArrayList<>();
        callBack.getValue().onSuccess(list);

        repository.getWeatherData(getWeatherCallback, 524901);
        verify(service, times(1)).getWeather(any(ApiService.GetWeatherCallBack.class), eq(524901));

        assertTrue(repository.cachedWeather != null);
    }

    @Test
    public void testErrorResponse() throws Exception {
        assertTrue(repository.cachedWeather == null);
        repository.getWeatherData(getWeatherCallback, 524901);
        verify(service).getWeather(callBack.capture(), eq(524901));
        callBack.getValue().onError("");

        assertTrue(repository.cachedWeather == null);
        repository.getWeatherData(getWeatherCallback, 524901);
        verify(service, times(2)).getWeather(any(ApiService.GetWeatherCallBack.class), eq(524901));
        assertTrue(repository.cachedWeather == null);
    }


    @Test
    public void testClearCache() throws Exception {
        assertTrue(repository.cachedWeather == null);
        repository.getWeatherData(getWeatherCallback, 524901);
        verify(service).getWeather(callBack.capture(), eq(524901));

        List<WeatherItems> list = new ArrayList<>();
        callBack.getValue().onSuccess(list);

        assertTrue(repository.cachedWeather != null);

        repository.clearCache();

        assertTrue(repository.cachedWeather == null);
    }

    @After
    public void tearDown() throws Exception {

    }

}