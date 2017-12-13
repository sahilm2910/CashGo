package com.learnacad.cashgo.ViewPagerAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.learnacad.cashgo.Fragments.Cafe;
import com.learnacad.cashgo.Fragments.FoodDrinksCover;
import com.learnacad.cashgo.Fragments.Saloon;
import com.learnacad.cashgo.Fragments.Spas;
import com.learnacad.cashgo.Fragments.ThingsToDo;

/**
 * Created by Sahil Malhotra on 16-09-2017.
 */

public class CoverPicViewPagerAdapter extends FragmentStatePagerAdapter {


    public CoverPicViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0){

            return new FoodDrinksCover();
        }else if(position == 1){

            return new Spas();
        }else if(position == 2){

            return new Saloon();
        }else if(position == 3){

            return new ThingsToDo();
        }else{

            return new Cafe();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
