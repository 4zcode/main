package com.example.myapplication;

import android.content.Context;
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

class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> {

    //Member variables
    public GradientDrawable mGradientDrawable;
    public ArrayList<Doctors> mDoctorsData;
    public Context mContext;

    DoctorsAdapter(Context context, ArrayList<Doctors> doctorData) {
        Log.d("akram","we stil alive adapter 1");

        this.mDoctorsData = doctorData;
        this.mContext = context;

        //Prepare gray placeholder
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);
        Log.d("akram","we stil alive adapter 3");

        //Make the placeholder same size as the images
        Drawable drawable = ContextCompat.getDrawable
                (mContext, R.drawable.img_badminton);
        if (drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }


    @Override
    public DoctorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("akram","we stil alive adapter 4");

        View view = LayoutInflater.from(mContext).inflate(R.layout.doctor_item, parent, false);
        Log.d("akram","we stil alive adapter 5");

        return new DoctorViewHolder(mContext, view, mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(DoctorViewHolder  holder, int position) {
        Log.d("akram","we stil alive adapter 6");

        //Get the current sport
        Doctors currentDoctor = mDoctorsData.get(position);
        Log.d("akram","we stil alive adapter 7");

        //Bind the data to the views
        holder.bindTo(currentDoctor);

    }


    @Override
    public int getItemCount() {
        Log.d("akram","we stil alive adapter 8");

        return mDoctorsData.size();
    }

    static class DoctorViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        //Member Variables for the holder data
        public TextView mNameText;
        public TextView mPlaceText;
        public String mSex;
        ImageView mDoctorImage;
        Context mCont;
        Doctors mCurrentDoctor;
        GradientDrawable mGradientDrawable;

        DoctorViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);
            Log.d("akram","we stil alive adapter 9");

            //Initialize the views
            mNameText = (TextView) itemView.findViewById(R.id.name_doctor);
            Log.d("akram","we stil alive adapter 10");

            mPlaceText = (TextView) itemView.findViewById(R.id.place_doctor);
            Log.d("akram","we stil alive adapter 11");

            mDoctorImage = (ImageView) itemView.findViewById(R.id.doctor_image);
            Log.d("akram","we stil alive adapter 12");

            mCont = context;
            mGradientDrawable = gradientDrawable;
            //Set the OnClickListener to the whole view
            Log.d("akram","we stil alive adapter 13");

            itemView.setOnClickListener(this);
        }

        void bindTo(Doctors currentDoctor) {
            //Populate the textviews with data
            Log.d("akram","we stil alive adapter 14");

            mNameText.setText(currentDoctor.getNameDoctor());
            Log.d("akram","we stil alive adapter 15");

            mPlaceText.setText(currentDoctor.getPlaceDoctor());
            Log.d("akram","we stil alive adapter 16");

            //Get the current sport
            mCurrentDoctor = currentDoctor;
            Log.d("akram","we stil alive adapter 17");

            mSex =currentDoctor.getSexDoctor().toLowerCase();
            Log.d("akram","we stil alive adapter 18");

            if (mSex.equals("m")) Glide.with(mCont).load(R.drawable.doctorm).placeholder(mGradientDrawable).into(mDoctorImage);
            else Glide.with(mCont).load(R.drawable.doctorf).placeholder(mGradientDrawable).into(mDoctorImage);
        }

        @Override
        public void onClick(View view) {
            Log.d("akram","we stil alive adapter 19");

        }
    }
}
