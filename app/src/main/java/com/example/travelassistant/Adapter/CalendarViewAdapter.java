package com.example.travelassistant.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class CalendarViewAdapter extends FragmentPagerAdapter {

    private List<Fragment> datas;
    public CalendarViewAdapter(FragmentManager fm, List<Fragment>list){
        super(fm);
        datas=list;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }
}
