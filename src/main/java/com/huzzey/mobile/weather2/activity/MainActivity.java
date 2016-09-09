package com.huzzey.mobile.weather2.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.huzzey.mobile.weather2.data.Injector;
import com.huzzey.mobile.weather2.R;
import com.huzzey.mobile.weather2.WeatherAdapter;
import com.huzzey.mobile.weather2.datatype.Weather;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {
    private final String LOG = getClass().getSimpleName();
    private ImageView backgroundImage;
    private CollapsingToolbarLayout collapsingToolbar;
    private WeatherAdapter weatherAdapter;
    private MainActivityPresenter presenter;
    private boolean actionBarClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.mipmap.icon);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.black));
        backgroundImage = (ImageView) findViewById(R.id.backdrop);
        RecyclerView listView = (RecyclerView) findViewById(R.id.list_view);
        listView.setLayoutManager(new LinearLayoutManager(this));
        weatherAdapter = new WeatherAdapter(MainActivity.this, new Weather[0]);
        listView.setAdapter(weatherAdapter);
        //listView.addItemDecoration(headersDecor);
        actionBarClicked = false;
        presenter = new MainActivityPresenter(this, Injector.getLocalRepository());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(!actionBarClicked) {
            actionBarClicked = true;
            //noinspection SimplifiableIfStatement
            if (id == R.id.action_location) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setItems(presenter.getLocationNames(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.choiceLocation(which);
                        dialog.dismiss();
                        actionBarClicked = false;
                    }
                }).create().show();

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void displayOffLineDialog() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        presenter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        presenter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void setBackgroundImage(Integer image) {
        backgroundImage.setImageDrawable(getResources().getDrawable(image));
    }

    @Override
    public void setToolbarTitle(String title) {
        collapsingToolbar.setTitle(title);
    }

    @Override
    public void setToolbarTitle(int title) {
        setToolbarTitle(getString(title));
    }

    @Override
    public void updateAdapter(Weather[] weather) {
        weatherAdapter.updateData(weather);
    }

    @Override
    public void displayToast() {
        Toast.makeText(MainActivity.this, "Failed to load weather", Toast.LENGTH_SHORT).show();
    }
}
