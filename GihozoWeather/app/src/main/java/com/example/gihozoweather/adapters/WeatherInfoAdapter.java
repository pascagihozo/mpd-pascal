package com.example.gihozoweather.adapters;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gihozoweather.R;
import com.example.gihozoweather.services.WeatherInfo;

import java.util.List;

public class WeatherInfoAdapter extends ArrayAdapter<WeatherInfo> {

    private final Context context;
    private final List<WeatherInfo> weatherInfoList;

    public WeatherInfoAdapter(@NonNull Context context, @NonNull List<WeatherInfo> weatherInfoList) {
        super(context, R.layout.weather_list_item, weatherInfoList);
        this.context = context;
        this.weatherInfoList = weatherInfoList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.weather_list_item, parent, false);

        TextView dateTextView = itemView.findViewById(R.id.date_text_view);
        TextView locationTextView = itemView.findViewById(R.id.location_text_view);
        TextView minTempTextView = itemView.findViewById(R.id.min_temp_text_view);
        TextView maxTempTextView = itemView.findViewById(R.id.max_temp_text_view);
        TextView windSpeedTextView = itemView.findViewById(R.id.wind_speed_text_view_list_item);
        TextView humidityTextView = itemView.findViewById(R.id.humidity_text_view_list_item);
        TextView pressureTextView = itemView.findViewById(R.id.pressure_text_view_list_item);
        TextView conditionTextView = itemView.findViewById(R.id.condition_text_view_list_item);

        WeatherInfo weatherInfo = weatherInfoList.get(position);

        dateTextView.setText("Date: " + weatherInfo.getDate());
        locationTextView.setText("Location: " + weatherInfo.getLocation());
        minTempTextView.setText("Min Temp: " + weatherInfo.getMinTemperature());
        maxTempTextView.setText("Max Temp: " + weatherInfo.getMaxTemperature());
        windSpeedTextView.setText("Wind Speed: " + weatherInfo.getWindSpeed());
        humidityTextView.setText("Humidity: " + weatherInfo.getHumidity());
        pressureTextView.setText("Pressure: " + weatherInfo.getPressure());
        conditionTextView.setText("Condition: " + weatherInfo.getCondition());


        return itemView;
    }
}
