package com.learnacad.cashgo.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learnacad.cashgo.Adapters.ContentFragmentAdapter;
import com.learnacad.cashgo.R;

import java.util.ArrayList;

/**
 * Created by Sahil Malhotra on 17-09-2017.
 */

public class ThingsToDoContent extends Fragment {

    RecyclerView recyclerView;
    ArrayList<String> titles;
    ContentFragmentAdapter contentFragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_content_layout,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.contentRecyclerViewList);
        titles = new ArrayList<>();
        contentFragmentAdapter = new ContentFragmentAdapter(getActivity(),titles);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(contentFragmentAdapter);
        fetchTitles();
        return view;
    }

    private void fetchTitles() {



        for(int i = 0; i < 5; ++i){

            titles.add("Title" + " " + i);
        }
        contentFragmentAdapter.notifyDataSetChanged();
    }
}
