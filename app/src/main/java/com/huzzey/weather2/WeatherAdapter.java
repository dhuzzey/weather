package com.huzzey.weather2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.huzzey.weather2.datatype.Weather;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.ButterKnife;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private final String IMG_URL = "http://openweathermap.org/img/w/%s.png";
    private Weather[] weather;
    private final LayoutInflater inflater;
    private RequestManager manager;

    public WeatherAdapter(Context context, Weather[] currentWeather) {
        this.weather = currentWeather;
        inflater = LayoutInflater.from(context);
        manager = Glide.with(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView text;
        private TextView headerText;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            text = itemView.findViewById(R.id.text);
            headerText = itemView.findViewById(R.id.headerText);
        }
    }

    @Override
    public int getItemCount() {
        return weather.length;
    }

    public void updateData(Weather[] weather){
        this.weather = weather;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPES.NORMAL) {
            return new ViewHolder(inflater.inflate(R.layout.item_weather, parent, false));
        } else {
            return new ViewHolder(inflater.inflate(R.layout.item_weather_header, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (weather[position].isHeader()) {
            return VIEW_TYPES.HEADER;
        } else {
            return VIEW_TYPES.NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Weather object = weather[position];
        if(object.isHeader()) {
            holder.headerText.setText(getDate(object.getDate()));
        } else {
            holder.text.setText(new StringBuilder(object.getTime()).append(" - ").append(object.getDescription()));
            manager.load(String.format(IMG_URL, object.getIcon())).into(holder.icon);
        }
    }

    private String getDate(String date){
        String response = "";
        try {
            Calendar calender = Calendar.getInstance(Locale.UK);
            calender.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date.trim()));
            DateFormat dateFormat = DateFormat.getDateInstance(SimpleDateFormat.MEDIUM);
            response = dateFormat.format(calender.getTimeInMillis());
        } catch (Exception e){}
        return response;
    }

    public class VIEW_TYPES {
        static final int HEADER = 1;
        static final int NORMAL = 2;
    }
}
