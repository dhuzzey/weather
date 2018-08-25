package com.huzzey.weather2.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.huzzey.weather2.R;
import com.huzzey.weather2.WeatherAdapter;
import com.huzzey.weather2.data.Injector;
import com.huzzey.weather2.datatype.Weather;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    @BindView(R.id.backdrop) ImageView backgroundImage;
    @BindView(R.id.toolbar_layout) CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.list_view) RecyclerView listView;

    private WeatherAdapter weatherAdapter;
    private MainActivityPresenter presenter;
    private boolean actionBarClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.mipmap.icon);

        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.black));

        listView.setLayoutManager(new LinearLayoutManager(this));
        weatherAdapter = new WeatherAdapter(MainActivity.this, new Weather[0]);
        listView.setAdapter(weatherAdapter);
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
        if(!actionBarClicked) {
            actionBarClicked = true;
            //noinspection SimplifiableIfStatement
            if (item.getItemId() == R.id.action_location) {

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
