package com.learnacad.cashgo.ViewPagerAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.learnacad.cashgo.Fragments.CafeContent;
import com.learnacad.cashgo.Fragments.FoodDrinksContent;
import com.learnacad.cashgo.Fragments.SaloonContent;
import com.learnacad.cashgo.Fragments.SpasContent;
import com.learnacad.cashgo.Fragments.ThingsToDoContent;

/**
 * Created by Sahil Malhotra on 16-09-2017.
 */

public class HomeContentViewPager extends FragmentPagerAdapter {

    public HomeContentViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0 : return new FoodDrinksContent();
            case 1 : return new SpasContent();
            case 2 : return new SaloonContent();
            case 3 : return new ThingsToDoContent();
            default: return new CafeContent();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
