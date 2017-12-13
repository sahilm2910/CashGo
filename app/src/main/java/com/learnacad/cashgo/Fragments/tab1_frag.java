package com.learnacad.cashgo.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learnacad.cashgo.R;

/**
 * Created by Vishal on 11/13/2017.
 */

public class tab1_frag extends Fragment {
    private static final String TAG ="Tab1_frag";

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);
        return view;

    }
}
