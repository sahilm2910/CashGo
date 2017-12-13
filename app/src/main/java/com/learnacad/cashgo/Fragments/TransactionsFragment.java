package com.learnacad.cashgo.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learnacad.cashgo.Adapters.SectionsPageAdapter;
import com.learnacad.cashgo.R;

/**
 * Created by Sahil Malhotra on 16-11-2017.
 */

public class TransactionsFragment extends Fragment {


    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transactions_layout,container,false);

        mSectionsPageAdapter = new SectionsPageAdapter(getChildFragmentManager());
        mViewPager =(ViewPager) view.findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout =(TabLayout)view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager)
    {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getChildFragmentManager());
        adapter.addFragment(new tab1_frag(),"Coupons");
        adapter.addFragment(new tab2_frag(),"History");
        adapter.addFragment(new tab3_frag(),"Booked_Deals");
        viewPager.setAdapter(adapter);
    }
}
