package com.example.myapplication.doctors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.myapplication.FragmentCommunications;
import com.example.myapplication.R;

import java.util.ArrayList;

import static com.example.myapplication.utilities.PreferenceUtilities.saveSpeciality;

class DoctorsSpecialisteAdapter extends RecyclerView.Adapter<DoctorsSpecialisteAdapter.DoctorSViewHolder> {

    //Member variables
    private GradientDrawable mGradientDrawable;
    private ArrayList<DoctorsSpecialistes> mDoctorsspecialistes;
    private Context mContext;
    private Activity activity;

    DoctorsSpecialisteAdapter(Activity activity,Context context, ArrayList<DoctorsSpecialistes> doctorData) {
        this.mDoctorsspecialistes = doctorData;
        this.mContext = context;
           this.activity = activity;
        //Prepare gray placeholder
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);
        //Make the placeholder same size as the images
        Drawable drawable = ContextCompat.getDrawable
                (mContext, R.drawable.profile);
        if (drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }


    @Override
    public DoctorSViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.specialite_item, parent, false);
        return new DoctorSViewHolder(activity,mContext, view, mGradientDrawable);
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
        return mDoctorsspecialistes.size();
    }

    static class DoctorSViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        //Member Variables for the holder data
        public TextView mNameText, mNumberText;
        ImageView mDoctorImage;
        Context mCont;
        Activity activity;
        DoctorsSpecialistes mCurrentDoctor;
        GradientDrawable mGradientDrawable;

        DoctorSViewHolder(Activity activity, Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);
            //Initialize the views
            mNameText = (TextView) itemView.findViewById(R.id.specialite_name);
            mNumberText = (TextView) itemView.findViewById(R.id.specialite_times);
            mDoctorImage = (ImageView) itemView.findViewById(R.id.spec_doctor_image);
            mCont = context;
            this.activity = activity;
            mGradientDrawable = gradientDrawable;
            //Set the OnClickListener to the whole view
            itemView.setOnClickListener(this);
        }

        void bindTo(DoctorsSpecialistes currentDoctor) {
            //Populate the textviews with data
            mNameText.setText(currentDoctor.getSpecialiste());
            String NBR = "0";
            switch (currentDoctor.getSpecialiste().toLowerCase()){
                case "médecin généraliste" :
                    NBR= "+3k";
                    break;
                case "cardiologue" :
                    NBR= "+1k";
                    break;
                case "psychologue" :
                    NBR= "+520";
                    break;
                default: NBR= "+10";
            }

            mNumberText.setText(NBR);
            //Get the current sport
            mCurrentDoctor = currentDoctor;
            Glide.with(mCont).load(R.drawable.profile).placeholder(mGradientDrawable).into(mDoctorImage);

        }

        @Override
        public void onClick(View view) {
            saveSpeciality(mCont,mNameText.getText().toString());
            Toast.makeText(mCont,"clicked",Toast.LENGTH_LONG).show();
            ViewPager tabHost = (ViewPager) activity.findViewById(R.id.dviewpager);
            tabHost.setCurrentItem(tabHost.getCurrentItem()+1);
        }
    }
}
