package com.example.gihozoweather.adapters;

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

public class TabPagerAdapter extends FragmentStateAdapter {

    public TabPagerAdapter(FragmentActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new OmanFragment();
            case 1:
                return new BangladeshFragment();
            case 2:
                return new MauritiusFragment();
            case 3:
                return new LondonFragment();
            case 4:
                return new NewYorkFragment();
            case 5:
                return new GlasgowFragment();
            default:
                throw new IllegalStateException("Unexpected position " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}