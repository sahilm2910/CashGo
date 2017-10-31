package com.learnacad.cashgo.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learnacad.cashgo.R;

import java.util.ArrayList;

/**
 * Created by Sahil Malhotra on 17-09-2017.
 */

public class ContentFragmentAdapter extends RecyclerView.Adapter<ContentFragmentAdapter.ContentFragmentViewHolder>{

    Context mContext;
    ArrayList<String> titles;

    public ContentFragmentAdapter(Context context,ArrayList<String> titles){

        this.titles = titles;
        this.mContext = context;
    }

    @Override
    public ContentFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout,parent,false);
        return new ContentFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContentFragmentViewHolder holder, int position) {
            holder.titleTextView.setText(titles.get(position));

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ContentFragmentViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;

        public ContentFragmentViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.nameOftheRestaurantItemTextView);
        }
    }
}
