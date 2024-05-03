

package com.example.gihozoweather.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gihozoweather.R;
import com.example.gihozoweather.activities.AboutActivity;
import com.example.gihozoweather.activities.DetailedActivity;
import com.example.gihozoweather.activities.MainActivity;
import com.example.gihozoweather.activities.MapActivity;
import com.example.gihozoweather.services.WeatherInfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class OmanFragment extends Fragment {
    private TextView weatherTextView;
    private Button viewDetailedButton;
    private TextView temperatureTextViewYesterday;
    private TextView weatherConditionTextViewYesterday;
    private TextView temperatureTextViewToday;
    private TextView weatherConditionTextViewToday;
    private TextView temperatureTextViewTomorrow;
    private TextView weatherConditionTextViewTomorrow;
    private TextView windSpeedTextView;
    private TextView humidityTextView;
    private TextView pressureTextView;

    public static OmanFragment newInstance(List<WeatherInfo> weatherInfoList) {
        OmanFragment fragment = new OmanFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("weatherInfoList", (ArrayList<? extends Parcelable>) weatherInfoList);
        fragment.setArguments(args);
        return fragment;
    }



    private BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oman, container, false);

        bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {

                    return true;
                } else if (item.getItemId() == R.id.navigation_map) {
                    // Navigate to MapActivity
                    navigateToMapActivity();
                    return true;
                } else if (item.getItemId() == R.id.navigation_about) {
                    // Navigate to AboutActivity
                    navigateToAboutActivity();
                    return true;
                } else {
                    return false;
                }
            }

        });

        viewDetailedButton = view.findViewById(R.id.view_detailed_button); // Find the button by its ID
        viewDetailedButton.setOnClickListener(new View.OnClickListener() { // Set onClickListener
            @Override
            public void onClick(View v) {
                List<WeatherInfo> weatherInfoList = getArguments().getParcelableArrayList("weatherInfoList");


                if (weatherInfoList != null && !weatherInfoList.isEmpty()) {
                    navigateToDetailedActivity(weatherInfoList);
                } else {
                    Log.e("OmanFragment", "Weather info list is empty");
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<WeatherInfo> weatherInfoList = getArguments().getParcelableArrayList("weatherInfoList");
        Log.d("QWE",weatherInfoList.toString());
        if (weatherInfoList != null && !weatherInfoList.isEmpty()) {
            WeatherInfo firstWeatherInfo = weatherInfoList.get(0);
            WeatherInfo second = weatherInfoList.get(1);
            WeatherInfo third = weatherInfoList.get(2);

            temperatureTextViewYesterday = view.findViewById(R.id.temperature_text_view_yesterday);
            temperatureTextViewYesterday.setText(third.getMinTemperature()); // Set yesterday's temperature

            weatherConditionTextViewYesterday = view.findViewById(R.id.weather_condition_text_view_yesterday);
            weatherConditionTextViewYesterday.setText(third.getCondition()); // Set yesterday's weather condition

            temperatureTextViewToday = view.findViewById(R.id.temperature_text_view_today);
            temperatureTextViewToday.setText(firstWeatherInfo.getMinTemperature()); // Set today's temperature

            weatherConditionTextViewToday = view.findViewById(R.id.weather_condition_text_view_today);
            weatherConditionTextViewToday.setText(firstWeatherInfo.getCondition()); // Set today's weather condition

            temperatureTextViewTomorrow = view.findViewById(R.id.temperature_text_view_tomorrow);
            temperatureTextViewTomorrow.setText(second.getMinTemperature()); // Set tomorrow's temperature

            weatherConditionTextViewTomorrow = view.findViewById(R.id.weather_condition_text_view_tomorrow);
            weatherConditionTextViewTomorrow.setText(second.getCondition()); // Set tomorrow's weather condition

            windSpeedTextView = view.findViewById(R.id.wind_speed_text_view);
            windSpeedTextView.setText(firstWeatherInfo.getWindSpeed()); // Set wind speed

            humidityTextView = view.findViewById(R.id.humidity_text_view);
            humidityTextView.setText(firstWeatherInfo.getHumidity()); // Set humidity

            pressureTextView = view.findViewById(R.id.pressure_text_view);
            pressureTextView.setText(firstWeatherInfo.getPressure()); // Set pressure
        } else {
            Log.e("OmanFragment", "Weather info list is empty");
        }
    }

    private void navigateToHomeActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    private void navigateToMapActivity() {
        Intent intent = new Intent(getActivity(), MapActivity.class);
        startActivity(intent);
    }

    private void navigateToAboutActivity() {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
    }

    private void navigateToDetailedActivity(List<WeatherInfo> weatherInfoList) {
        Intent intent = new Intent(getActivity(), DetailedActivity.class);
        intent.putParcelableArrayListExtra("weatherInfoList", (ArrayList<? extends Parcelable>) weatherInfoList);
        startActivity(intent);
    }
}
