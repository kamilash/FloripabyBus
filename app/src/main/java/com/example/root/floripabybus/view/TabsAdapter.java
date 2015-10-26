package com.example.root.floripabybus.view;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class TabsAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "Route", "Weekdays", "Saturday", "Sunday" };
    private Context context;

    public TabsAdapter(FragmentManager fm, Context contex) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {

        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        return PageFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
