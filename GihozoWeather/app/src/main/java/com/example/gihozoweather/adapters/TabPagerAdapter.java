package com.example.gihozoweather.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gihozoweather.fragments.BangladeshFragment;
import com.example.gihozoweather.fragments.GlasgowFragment;
import com.example.gihozoweather.fragments.LondonFragment;
import com.example.gihozoweather.fragments.MauritiusFragment;
import com.example.gihozoweather.fragments.NewYorkFragment;
import com.example.gihozoweather.fragments.OmanFragment;
import com.example.gihozoweather.fragments.WeatherFragment;
import com.example.gihozoweather.services.WeatherInfo;

import java.util.List;



public class TabPagerAdapter extends FragmentStateAdapter {
    private List<List<WeatherInfo>> weatherDataLists;

    public TabPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<List<WeatherInfo>> weatherDataLists) {
        super(fragmentActivity);
        this.weatherDataLists = weatherDataLists;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        List<WeatherInfo> weatherInfoList = weatherDataLists.get(position);
        if (weatherInfoList.isEmpty()) {
            // Handle empty list case
            Log.e("TabPagerAdapter", "Weather info list is empty for position: " + position);
            return  null; // Replace DefaultFragment with your default fragment class
        } else {
            WeatherInfo weatherInfo = weatherInfoList.get(0); // Assuming the first item in the list is the primary weather info
            switch (position) {
                case 0:
                    return OmanFragment.newInstance(weatherInfoList);
                case 1:
                    return BangladeshFragment.newInstance(weatherInfoList);
                case 2:
                    return MauritiusFragment.newInstance(weatherInfoList);
                case 3:
                    return LondonFragment.newInstance(weatherInfoList);
                case 4:
                    return NewYorkFragment.newInstance(weatherInfoList);
                case 5:
                    return GlasgowFragment.newInstance(weatherInfoList);
                // Add more cases as needed
                default:
                    // Log the unexpected position for debugging
                    Log.w("TabPagerAdapter", "Unexpected position: " + position);
                    return NewYorkFragment.newInstance(weatherInfoList);// Replace DefaultFragment with your default fragment class
            }
        }
    }

    @Override
    public int getItemCount() {
        return weatherDataLists.size();
    }
}





