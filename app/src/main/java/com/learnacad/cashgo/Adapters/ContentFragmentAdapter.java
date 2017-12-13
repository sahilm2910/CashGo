package com.learnacad.cashgo.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.learnacad.cashgo.R;

import java.util.ArrayList;

/**
 * Created by Sahil Malhotra on 17-09-2017.
 */

public class ContentFragmentAdapter extends RecyclerView.Adapter<ContentFragmentAdapter.ContentFragmentViewHolder>{

    Context mContext;
    ArrayList<String> titles;
    String whichList;

    public ContentFragmentAdapter(Context context,ArrayList<String> titles,String whichList){

        this.titles = titles;
        this.mContext = context;
        this.whichList = whichList;
    }

    @Override
    public ContentFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout,parent,false);
        return new ContentFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContentFragmentViewHolder holder, int position) {
            holder.titleTextView.setText(titles.get(position));

            if(whichList.contentEquals("Food")){

                holder.imageView.setImageResource(R.drawable.food);
            }
            else if(whichList.contentEquals("Spa")){

                holder.imageView.setImageResource(R.drawable.spa);
            }
            else if(whichList.contentEquals("salon")){

                holder.imageView.setImageResource(R.drawable.salon);
            }
            else if(whichList.contentEquals("td")){

                holder.imageView.setImageResource(R.drawable.td);
            }
            else if(whichList.contentEquals("cafe")){

                holder.imageView.setImageResource(R.drawable.cafes);
            }

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ContentFragmentViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        ImageView imageView;

        public ContentFragmentViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.itemImageView);
            titleTextView = (TextView) itemView.findViewById(R.id.nameOftheRestaurantItemTextView);
        }
    }
}
