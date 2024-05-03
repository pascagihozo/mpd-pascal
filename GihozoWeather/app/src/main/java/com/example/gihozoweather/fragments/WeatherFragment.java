package com.example.gihozoweather.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gihozoweather.R;
import com.example.gihozoweather.services.WeatherInfo;

import java.util.List;

public class WeatherFragment extends Fragment {

    private List<WeatherInfo> weatherInfoList;

    // UI components
    private TextView weatherTextView;
    private TextView temperatureYesterday, temperatureToday, temperatureTomorrow;
    private TextView conditionYesterday, conditionToday, conditionTomorrow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bangladesh, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI components
        weatherTextView = view.findViewById(R.id.weatherTextView);
        temperatureYesterday = view.findViewById(R.id.temperature_text_view_yesterday);
        temperatureToday = view.findViewById(R.id.temperature_text_view_today);
        temperatureTomorrow = view.findViewById(R.id.temperature_text_view_tomorrow);
        conditionYesterday = view.findViewById(R.id.weather_condition_text_view_yesterday);
        conditionToday = view.findViewById(R.id.weather_condition_text_view_today);
        conditionTomorrow = view.findViewById(R.id.weather_condition_text_view_tomorrow);

        // Update UI with weather information
        if (weatherInfoList != null && weatherInfoList.size() >= 4) {
            // Set relevant details for current weather
            WeatherInfo currentWeather = weatherInfoList.get(0);
            weatherTextView.setText(currentWeather.getLocation());
            // Set background based on current weather condition, if needed

            // Set temperatures and conditions for yesterday, today, and tomorrow
            WeatherInfo yesterdayWeather = weatherInfoList.get(1);
            WeatherInfo todayWeather = weatherInfoList.get(2);
            WeatherInfo tomorrowWeather = weatherInfoList.get(3);

            // Set temperatures
            if (yesterdayWeather != null) {
                temperatureYesterday.setText(yesterdayWeather.getMinTemperature());
                conditionYesterday.setText(yesterdayWeather.getCondition());
            }

            if (todayWeather != null) {
                temperatureToday.setText(todayWeather.getMinTemperature());
                conditionToday.setText(todayWeather.getCondition());
            }

            if (tomorrowWeather != null) {
                temperatureTomorrow.setText(tomorrowWeather.getMinTemperature());
                conditionTomorrow.setText(tomorrowWeather.getCondition());
            }

            // Update other UI components (wind speed, humidity, pressure, etc.) similarly
        } else {
            // Handle the case where the list doesn't have enough elements
            // Log an error or show a message to the user
        }
    }


    public void setWeatherInfoList(List<WeatherInfo> weatherInfoList) {
        this.weatherInfoList = weatherInfoList;
        // Update UI with weather information
        if (getView() != null) {
            onViewCreated(getView(), null);
        }
    }
}
