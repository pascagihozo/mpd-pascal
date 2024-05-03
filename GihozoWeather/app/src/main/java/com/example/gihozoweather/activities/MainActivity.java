package com.example.gihozoweather.activities;

import static com.example.gihozoweather.parsers.DataFetcher.fetchWeatherDataInBackground;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.gihozoweather.R;
import com.example.gihozoweather.adapters.TabPagerAdapter;
import com.example.gihozoweather.services.WeatherInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(2);



        List<List<WeatherInfo>> allWeatherInfoLists = new ArrayList<>();

        // URLs to fetch weather data from
        String[] urls = {
                "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286",
                "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241",
                "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154",
                "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743",
                "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581",
                "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579"
        };

        // Fetch weather data from each URL and add it to the list
        for (String url : urls) {
            List<WeatherInfo> weatherInfoList = fetchWeatherDataInBackground(url);
            if (weatherInfoList!= null) {
                allWeatherInfoLists.add(weatherInfoList);

            }
        }
        Log.d("XCV",allWeatherInfoLists.toString());

        // Update the ViewPager2 adapter with the fetched data
        viewPager.setAdapter(new TabPagerAdapter(this, allWeatherInfoLists));
    }

}


