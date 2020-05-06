package com.example.myapplication.doctors;

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
import com.example.myapplication.R;

import java.util.ArrayList;

class DoctorsSpecialisteAdapter extends RecyclerView.Adapter<DoctorsSpecialisteAdapter.DoctorSViewHolder> {

    //Member variables
    public GradientDrawable mGradientDrawable;
    public ArrayList<DoctorsSpecialistes> mDoctorsspecialistes;
    public Context mContext;

    DoctorsSpecialisteAdapter(Context context, ArrayList<DoctorsSpecialistes> doctorData) {
        this.mDoctorsspecialistes = doctorData;
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
    public DoctorSViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.specialite_item, parent, false);
        return new DoctorSViewHolder(mContext, view, mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(DoctorSViewHolder  holder, int position) {
        //Get the current sport
        DoctorsSpecialistes currentDoctor = mDoctorsspecialistes.get(position);
        //Bind the data to the views
        holder.bindTo(currentDoctor);

    }


    @Override
    public int getItemCount() {
        Log.d("akram","we stil alive adapter 8");

        return mDoctorsspecialistes.size();
    }

    static class DoctorSViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        //Member Variables for the holder data
        public TextView mNameText;
        ImageView mDoctorImage;
        Context mCont;
        DoctorsSpecialistes mCurrentDoctor;
        GradientDrawable mGradientDrawable;

        DoctorSViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);
            Log.d("akram","we stil alive adapter 9");

            //Initialize the views
            mNameText = (TextView) itemView.findViewById(R.id.specialite_name);
            Log.d("akram","we stil alive adapter 10");


            mDoctorImage = (ImageView) itemView.findViewById(R.id.spec_doctor_image);
            Log.d("akram","we stil alive adapter 12");

            mCont = context;
            mGradientDrawable = gradientDrawable;
            //Set the OnClickListener to the whole view
            Log.d("akram","we stil alive adapter 13");

            itemView.setOnClickListener(this);
        }

        void bindTo(DoctorsSpecialistes currentDoctor) {
            //Populate the textviews with data
            Log.d("akram","we stil alive adapter 14");

            mNameText.setText(currentDoctor.getSpecialiste());
            Log.d("akram","we stil alive adapter 15");



            //Get the current sport
            mCurrentDoctor = currentDoctor;
            Log.d("akram","we stil alive adapter 17");


            Glide.with(mCont).load(R.drawable.doctorm).placeholder(mGradientDrawable).into(mDoctorImage);
        }

        @Override
        public void onClick(View view) {
            Log.d("akram","we stil alive adapter 19");

        }
    }
}
