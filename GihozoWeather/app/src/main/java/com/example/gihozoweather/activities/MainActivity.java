package com.example.gihozoweather.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.gihozoweather.R;
import com.example.gihozoweather.adapters.TabPagerAdapter;
import com.example.gihozoweather.services.WeatherFetcher;
import com.example.gihozoweather.services.WeatherUpdateService;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(new TabPagerAdapter(this));

        // Call the fetchAndUpdateWeatherData method
        WeatherFetcher.fetchAndUpdateWeatherData();

    }
}
