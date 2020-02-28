package com.example.myapplication.Hospitals;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.ArrayList;

class HopitalsAdapter extends RecyclerView.Adapter<HopitalsAdapter.HopitalsViewHolder> {

    //Member variables
    public GradientDrawable mGradientDrawable;
    public ArrayList<Hopital> mHopitalsData;
    public static Context mContext;

    HopitalsAdapter(Context context, ArrayList<Hopital> HopitalsData) {
        this.mHopitalsData = HopitalsData;
        this.mContext = context;

        //Prepare gray placeholder
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);

        //Make the placeholder same size as the images
        Drawable drawable = ContextCompat.getDrawable
                (mContext, R.drawable.doctorm);
        if (drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }


    @Override
    public HopitalsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.hospital_item_view, parent, false);
        return new HopitalsViewHolder(mContext, view, mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(HopitalsViewHolder holder, int position) {

        //Get the current sport
        Hopital currentHopital = mHopitalsData.get(position);

        //Bind the data to the views
        holder.bindTo(currentHopital);

    }


    @Override
    public int getItemCount() {
        return mHopitalsData.size();
    }

    static class HopitalsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        //Member Variables for the holder data
        public TextView mHopitalNameTextView;
        public TextView mHopitalPlaceTextView;
        public TextView mHopitalContactTextView;
        ImageView mHopitalImage;
        Context mCont;
        Hopital mCurrentHopital;
        GradientDrawable mGradientDrawable;

        HopitalsViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);

            //Initialize the views
            mHopitalNameTextView = (TextView) itemView.findViewById(R.id.hopital_name);
            mHopitalPlaceTextView = (TextView) itemView.findViewById(R.id.hopital_place);
            mHopitalContactTextView = (TextView) itemView.findViewById(R.id.hopital_contact);
            mHopitalImage = (ImageView) itemView.findViewById(R.id.hopital_image);

            mCont = context;
            mGradientDrawable = gradientDrawable;

            //Set the OnClickListener to the whole view
            itemView.setOnClickListener(this);
        }

        void bindTo(Hopital currentHopital) {
            //Populate the textviews with data
            mHopitalNameTextView.setText(currentHopital.getHopitalName());
            mHopitalPlaceTextView.setText(currentHopital.getHopitalPlace());
            mHopitalContactTextView.setText(currentHopital.getHopitalContact());

            //Get the current sport
            mCurrentHopital = currentHopital;

            Glide.with(mCont).load(currentHopital.getImageResource()).placeholder(mGradientDrawable).into(mHopitalImage);
        }

        @Override
        public void onClick(View view) {

        }
    }
}