//package com.example.gihozoweather.fragments;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.fragment.app.Fragment;
//
//import com.example.gihozoweather.R;
//
//public class MauritiusFragment extends Fragment {
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_mauritius, container, false);
//    }
//}


package com.example.gihozoweather.fragments;

//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.example.gihozoweather.R;
//import com.example.gihozoweather.services.WeatherInfo;
//
//
//public class MauritiusFragment extends Fragment {
//    public static MauritiusFragment newInstance(WeatherInfo weatherInfo) {
//        MauritiusFragment fragment = new MauritiusFragment();
//        Bundle args = new Bundle();
//        args.putParcelable("weatherInfo", weatherInfo);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_mauritius, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        WeatherInfo weatherInfo = getArguments().getParcelable("weatherInfo");
//        Log.d("DFH",weatherInfo.toString());
//    }
//}
//
//



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
import com.example.gihozoweather.activities.DetailedActivity;
import com.example.gihozoweather.services.WeatherInfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MauritiusFragment extends Fragment {
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

    public static MauritiusFragment newInstance(List<WeatherInfo> weatherInfoList) {
        MauritiusFragment fragment = new MauritiusFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("weatherInfoList", (ArrayList<? extends Parcelable>) weatherInfoList);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_mauritius, container, false);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mauritius, container, false);



        viewDetailedButton = view.findViewById(R.id.view_detailed_button); // Find the button by its ID
        viewDetailedButton.setOnClickListener(new View.OnClickListener() { // Set onClickListener
            @Override
            public void onClick(View v) {
                List<WeatherInfo> weatherInfoList = getArguments().getParcelableArrayList("weatherInfoList");


                if (weatherInfoList != null && !weatherInfoList.isEmpty()) {
                    navigateToDetailedActivity(weatherInfoList);
                } else {
                    Log.e("MauritiusFragment", "Weather info list is empty");
                }
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<WeatherInfo> weatherInfoList = getArguments().getParcelableArrayList("weatherInfoList");
        if (weatherInfoList != null && !weatherInfoList.isEmpty()) {
            WeatherInfo firstWeatherInfo = weatherInfoList.get(0);
            WeatherInfo second = weatherInfoList.get(1);
            WeatherInfo third = weatherInfoList.get(2);

//            weatherTextView = view.findViewById(R.id.weatherTextView);
//            weatherTextView.setText(firstWeatherInfo.getLocation()); // Set location

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
            Log.e("MauritiusFragment", "Weather info list is empty");
        }
    }

    private void navigateToDetailedActivity(List<WeatherInfo> weatherInfoList) {
        Intent intent = new Intent(getActivity(), DetailedActivity.class);
        intent.putParcelableArrayListExtra("weatherInfoList", (ArrayList<? extends Parcelable>) weatherInfoList);
        startActivity(intent);
    }
}
