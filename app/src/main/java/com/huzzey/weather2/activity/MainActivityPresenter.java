package com.huzzey.weather2.activity;

import android.os.Bundle;


import com.huzzey.weather2.R;
import com.huzzey.weather2.data.Repository;
import com.huzzey.weather2.datatype.Weather;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by darren.huzzey on 07/06/16.
 */
public class MainActivityPresenter implements MainActivityContract.Action {
    private final String LOG = getClass().getSimpleName();
    private MainActivityContract.View view;
    private Repository repository;
    private final int[] locationIds = new int[]{524901, 5128638};
    private final String[] locationNames = new String[]{"London", "New York"};
    private int selectedValue;

    public MainActivityPresenter(MainActivityContract.View v, Repository repository) {
        view = v;
        this.repository = repository;
        selectedValue = 0;
    }

    public String[] getLocationNames(){
        return locationNames;
    }

    @Override
    public void getData(boolean refresh) {


    }

    @Override
    public void choiceLocation(int location) {
        if(selectedValue != location){
            repository.clearCache();
            selectedValue = location;
        }
        view.setBackgroundImage(location == 0 ? R.drawable.london : R.drawable.new_york);
        view.setToolbarTitle(locationNames[location]);

        repository.getWeatherData(locationIds[location])
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Weather>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Weather> weathers) {
                        view.updateAdapter(weathers.toArray(new Weather[weathers.size()]));
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.displayToast();
                    }
                });
    }

    @Override
    public void onResume() {
        choiceLocation(selectedValue);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("id", selectedValue);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        selectedValue = savedInstanceState.getInt("id");
    }
}
