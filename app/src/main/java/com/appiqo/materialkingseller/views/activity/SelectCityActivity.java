package com.appiqo.materialkingseller.views.activity;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.views.fragment.FragmentCitySearch;
import com.appiqo.materialkingseller.views.fragment.FragmentCustomeCity;
import com.appiqo.materialkingseller.views.fragment.FragmentGPS;

import java.io.InputStream;

public class SelectCityActivity extends AppCompatActivity {

    TabLayout tabsCities;
    ViewPager viewPagerCities;
    ViewPagerAdapter vpAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);


        initialize();

        vpAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerCities.setAdapter(vpAdapter);
        tabsCities.setupWithViewPager(viewPagerCities);
    }

    private void initialize() {
        tabsCities = findViewById(R.id.tabs_cities);
        tabsCities = findViewById(R.id.viewPager_cities);
    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new FragmentCitySearch();
            } else if (position == 1) {
                fragment = new FragmentCustomeCity();
            } else if (position == 2) {
                fragment = new FragmentGPS();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            if (position == 0) {
                title = "City Search";
            } else if (position == 1) {
                title = "Custom City";
            } else if (position == 2) {
                title = "G.P.S.";
            }
            return title;
        }
    }


}
