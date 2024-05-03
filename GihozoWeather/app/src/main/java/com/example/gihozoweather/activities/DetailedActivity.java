package com.example.gihozoweather.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.gihozoweather.R;



import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gihozoweather.R;
import com.example.gihozoweather.adapters.WeatherInfoAdapter;
import com.example.gihozoweather.services.WeatherInfo;

import java.util.ArrayList;
import java.util.List;

public class DetailedActivity extends AppCompatActivity {

    private ListView weatherListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        weatherListView = findViewById(R.id.weatherListView); // Assuming your ListView has this id

        // Get the list of weather info objects passed from the fragment
        List<WeatherInfo> weatherInfoList = getIntent().getParcelableArrayListExtra("weatherInfoList");

        // Check if the list is not null or empty
        if (weatherInfoList != null && !weatherInfoList.isEmpty()) {
            // Create an adapter to display the weather info in the list view
            WeatherInfoAdapter adapter = new WeatherInfoAdapter(this, weatherInfoList);
            weatherListView.setAdapter(adapter);
        } else {
            // Handle the case where there is no data to display (e.g., show a message)
            TextView noDataTextView = findViewById(R.id.noDataTextView); // Assuming you have a TextView for this purpose
            noDataTextView.setVisibility(View.VISIBLE);
            noDataTextView.setText("No weather information available");
        }
    }
}
