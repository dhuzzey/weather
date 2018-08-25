package com.huzzey.weather2.activity;

import android.os.Bundle;

import com.huzzey.weather2.datatype.Weather;


/**
 * Created by darren.huzzey on 07/06/16.
 */
public interface MainActivityContract {
    interface View {
        void setBackgroundImage(Integer image);
        void setToolbarTitle(String title);
        void setToolbarTitle(int title);
        void updateAdapter(Weather[] weather);
        void displayToast();
        void displayOffLineDialog();
    }

    interface Action {
        void getData(boolean refresh);
        void onResume();
        void choiceLocation(int location);
        void onDestroy();
        void onSaveInstanceState(Bundle outState);
        void onRestoreInstanceState(Bundle savedInstanceState);
    }
}
