package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

class HopitalsAdapter extends RecyclerView.Adapter<HopitalsAdapter.SportsViewHolder> {

    //Member variables
    public GradientDrawable mGradientDrawable;
    public ArrayList<Hopital> mHopitalsData;
    public static Context mContext;

    HopitalsAdapter(Context context, ArrayList<Hopital> sportsData) {
        this.mHopitalsData = sportsData;
        this.mContext = context;

        //Prepare gray placeholder
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);

        //Make the placeholder same size as the images
        Drawable drawable = ContextCompat.getDrawable
                (mContext, R.drawable.img_badminton);
        if (drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }


    @Override
    public SportsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.hospital_item_view, parent, false);
        return new SportsViewHolder(mContext, view, mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(SportsViewHolder holder, int position) {

        //Get the current sport
        Hopital currentHopital = mHopitalsData.get(position);

        //Bind the data to the views
        holder.bindTo(currentHopital);

    }


    @Override
    public int getItemCount() {
        return mHopitalsData.size();
    }

    static class SportsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        //Member Variables for the holder data
        public TextView mTitleText;
        public TextView mInfoText;
        public TextView mCate;
        ImageView mSportsImage;
        Context mCont;
        Hopital mCurrentHopital;
        GradientDrawable mGradientDrawable;

        SportsViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);

            //Initialize the views
            mTitleText = (TextView) itemView.findViewById(R.id.title);
            mInfoText = (TextView) itemView.findViewById(R.id.subTitle);
            mCate = (TextView) itemView.findViewById(R.id.newsTitle);
            mSportsImage = (ImageView) itemView.findViewById(R.id.sportsImage);

            mCont = context;
            mGradientDrawable = gradientDrawable;

            //Set the OnClickListener to the whole view
            itemView.setOnClickListener(this);
        }

        void bindTo(Hopital currentHopital) {
            //Populate the textviews with data
            mTitleText.setText(currentHopital.getTitle());
            mInfoText.setText(currentHopital.getInfo());
            mCate.setText(currentHopital.getCate());

            //Get the current sport
            mCurrentHopital = currentHopital;

            Glide.with(mCont).load(currentHopital.
                    getImageResource()).placeholder(mGradientDrawable).into(mSportsImage);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
