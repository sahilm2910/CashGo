package com.learnacad.cashgo.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.learnacad.cashgo.R;

/**
 * Created by Sahil Malhotra on 16-11-2017.
 */

public class PartnersFragment extends android.support.v4.app.Fragment {


    int [] IMAGES={R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,R.drawable.image5};
    String [] NAMES={"mcd","dominos","kfc","qds","ccd"};
    String [] DESCRIPTION={"burgers","pizza","chicken","continental","coffee"};
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.partners_layout,container,false);
        ListView listView = view.findViewById(R.id.select_dialog_listview);
        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);
        return view;
    }

    class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.partner_item_layout,null);

            ImageView imageView=(ImageView)view.findViewById(R.id.imageView);
            TextView textView_name =(TextView)view.findViewById(R.id.textView_name);
            TextView textView_description =(TextView)view.findViewById(R.id.textView_description);

            imageView.setImageResource(IMAGES[i]);
            textView_name.setText(NAMES[i]);
            textView_description.setText(DESCRIPTION[i]);

            return view;
        }
    }
}
