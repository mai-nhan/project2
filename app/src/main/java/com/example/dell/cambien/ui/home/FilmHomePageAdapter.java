package com.example.dell.cambien.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dell.cambien.ui.home.home.HomeFragment;

/**
 * Created by DELL on 5/10/2018.
 */

public class FilmHomePageAdapter extends FragmentPagerAdapter {
    HomeFragment homeFragment;
    SearchFragment searchFragment;
    public FilmHomePageAdapter(FragmentManager fm) {
        super(fm);
        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return homeFragment;
            case 1:
                return searchFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
