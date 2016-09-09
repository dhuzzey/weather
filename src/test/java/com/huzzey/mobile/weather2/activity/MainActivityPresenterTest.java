package com.huzzey.mobile.weather2.activity;

import android.graphics.Movie;

import com.huzzey.mobile.weather2.R;
import com.huzzey.mobile.weather2.data.Repository;
import com.huzzey.mobile.weather2.datatype.Weather;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by darren.huzzey on 09/09/2016.
 */
public class MainActivityPresenterTest {
    private MainActivityPresenter presenter;
    private Map<Integer,String> map;

    @Mock
    private MainActivityContract.View view;

    @Mock
    private Repository repository;

    @Captor
    private ArgumentCaptor<Repository.GetWeatherCallback> getWeatherCallbackArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new MainActivityPresenter(view, repository);
        map = new HashMap<>();
        map.put(524901,"London");
        map.put(5128638,"New York");
    }

    @Test
    public void testInitial() throws Exception {
        presenter.onResume();
        verify(repository).getWeatherData(getWeatherCallbackArgumentCaptor.capture(), eq(524901));
        List<Weather> list = new ArrayList<>();
        getWeatherCallbackArgumentCaptor.getValue().onSuccess(list);

        verify(view).updateAdapter(list.toArray(new Weather[list.size()]));
        verify(view).setToolbarTitle(map.get(524901));
        verify(view).setBackgroundImage(R.drawable.london);
    }


    @Test
    public void testChangeLocation() throws Exception {
        presenter.onResume();
        presenter.choiceLocation(1);
        verify(repository).clearCache();
        verify(repository).getWeatherData(getWeatherCallbackArgumentCaptor.capture(), eq(5128638));
        List<Weather> list = new ArrayList<>();
        getWeatherCallbackArgumentCaptor.getValue().onSuccess(list);

        verify(view).updateAdapter(list.toArray(new Weather[list.size()]));
        view.setToolbarTitle(map.get(5128638));
        verify(view).setBackgroundImage(R.drawable.new_york);
    }

    @Test
    public void testClearCache() throws Exception {
        presenter.onResume();
        presenter.choiceLocation(0);
        verify(repository, never()).clearCache();

        presenter.choiceLocation(1);
        verify(repository).clearCache();

    }

    @Test
    public void testError() throws Exception {
        presenter.onResume();
        verify(repository).getWeatherData(getWeatherCallbackArgumentCaptor.capture(), eq(524901));
        getWeatherCallbackArgumentCaptor.getValue().onFailure("error");

        verify(view).displayToast();
    }

    @After
    public void tearDown() throws Exception {

    }

}