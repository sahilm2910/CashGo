package com.learnacad.cashgo.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learnacad.cashgo.R;
import com.learnacad.cashgo.ViewPagerAdapters.CoverPicViewPagerAdapter;
import com.learnacad.cashgo.ViewPagerAdapters.HomeContentViewPager;

/**
 * Created by Sahil Malhotra on 17-09-2017.
 */

public class HomeHoldingFragment extends Fragment {

    ViewPager coverPicViewPager;
    ViewPager contentViewPager;
    TabLayout tabLayout;
    private static String[] tabTitles = {"FOOD & DRINKS","SPAS","SALON","THINGS TO DO","CAFE"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout,container,false);

        contentViewPager = (ViewPager) view.findViewById(R.id.homeListContentViewPager);
        coverPicViewPager = (ViewPager) view.findViewById(R.id.coverPicViewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.homeTabLayout);

        for(int i = 0; i < 5; ++i){

            tabLayout.addTab(tabLayout.newTab().setText(tabTitles[i]));
        }

        final CoverPicViewPagerAdapter coverPicViewPagerAdapter = new CoverPicViewPagerAdapter(getFragmentManager());
        HomeContentViewPager homeContentViewPager = new HomeContentViewPager(getFragmentManager());

        contentViewPager.setAdapter(homeContentViewPager);
        coverPicViewPager.setAdapter(coverPicViewPagerAdapter);

        coverPicViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        contentViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                coverPicViewPager.setCurrentItem(tab.getPosition());
                contentViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }
}
