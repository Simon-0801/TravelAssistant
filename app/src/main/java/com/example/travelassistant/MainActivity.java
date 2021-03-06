package com.example.travelassistant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.example.travelassistant.Adapter.CalendarViewAdapter;
import com.example.travelassistant.Fragment.MyFragment;
import com.example.travelassistant.RoutePlanActivity;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> frags;
    private List<String> mDataTitleList;
    private ViewPager pager;
    private MagicIndicator magicIndicator;
    private ActionBar toolbar2;
    private TextView dayshow;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    private int shouldShow = 1;
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private CompactCalendarView compactCalendarView;
    private TextView tv_tab_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        tv_tab_map = findViewById(R.id.tab_menu_map);

        pager = (ViewPager) findViewById(R.id.view_page);
        initData();
        CalendarViewAdapter adapter = new CalendarViewAdapter(getSupportFragmentManager(), frags);
        pager.setAdapter(adapter);
        initIndicator();

        //----------------indicator指示器点击事件-------------------
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (position == 1) {

                }
                if (position == 0) {
                    compactCalendarView = (CompactCalendarView) findViewById(R.id.calender);
                    compactCalendarView.showCalendarWithAnimation();
                    dayshow = (TextView) findViewById(R.id.dayshow);
                    dayshow.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
                }
            }

            @Override
            public void onPageSelected(int position) {
                magicIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }
        });
        ViewPagerHelper.bind(magicIndicator, pager);
        //-------------日历点击事件----------

        tv_tab_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RoutePlanActivity.class);
                startActivity(intent);
            }
        });
    }

    //-----------------------------初始化导航器------------------------------
    private void initIndicator() {
        magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataTitleList == null ? 0 : mDataTitleList.size();
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(getResources().getColor(R.color.darkSilver));
                colorTransitionPagerTitleView.setSelectedColor(getResources().getColor(R.color.title));
                colorTransitionPagerTitleView.setText(mDataTitleList.get(i));
                //colorTransitionPagerTitleView.setBackgroundColor(getResources().getColor(R.color.title));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pager.setCurrentItem(i);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                /*LinePagerIndicator pagerIndicator = new LinePagerIndicator(context);
                pagerIndicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);*/
                BezierPagerIndicator pagerIndicator = new BezierPagerIndicator(context);
                pagerIndicator.setColors(getResources().getColor(R.color.title));
                return pagerIndicator;
            }
        });
        commonNavigator.setAdjustMode(true);
        magicIndicator.setNavigator(commonNavigator);
    }

    //--------------------初始化所需的碎片和标题-----------------------
    private void initData() {
        frags = new ArrayList<>();
        frags.add(MyFragment.newInstance(R.layout.fragment_calendar));
        frags.add(MyFragment.newInstance(R.layout.fragment_textpad));

        mDataTitleList = new ArrayList<>();
        mDataTitleList.add("Calendar");
        mDataTitleList.add("TextPad");
    }

}
