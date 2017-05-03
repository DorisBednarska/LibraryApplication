package com.example.rc.samples.views.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by RaVxp on 25.04.2017.
 */

public class HomeAdapter extends FragmentPagerAdapter {

    public HomeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return ListFragment.newInstance(false);
            }
            case 1: {
                return ListFragment.newInstance(true);
            }
            case 2: {
                return FormFragment.newInstance();
            }

            default: {
                return ListFragment.newInstance(false);
            }
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Wszystkie";
            case 1:
                return "Moje";
            case 2:
                return "Panel Admina";
        }
        return null;
    }
}