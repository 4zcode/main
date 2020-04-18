package com.example.myapplication.doctors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorSViewHolder> {

    //Member variables
    public GradientDrawable mGradientDrawable;
    public ArrayList<Doctors> mDoctors;
    public Context mContext;
    private ArrayList<Doctors> mDoctorArray = new ArrayList<>();

    DoctorsAdapter(Context context, ArrayList<Doctors> doctorData) {
        this.mDoctors = doctorData;
        this.mContext = context;
        this.mDoctorArray.addAll(doctorData);
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
    public void filter(String text) {
        if(text.isEmpty()){
            mDoctors.clear();
            mDoctors.addAll(mDoctorArray);
        } else{
            ArrayList<Doctors> result = new ArrayList<>();
            text = text.toLowerCase();
            for(Doctors item: mDoctorArray){
                //match by name or phone
                if(item.getNameDoctor().toLowerCase().contains(text) ||
                        item.getSpec().toLowerCase().contains(text)){
                    result.add(item);
                }
            }
            mDoctors.clear();
            mDoctors.addAll(result);
        }
        notifyDataSetChanged();
    }


    @Override
    public DoctorSViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.doctor_item, parent, false);
        return new DoctorSViewHolder(mContext, view, mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(DoctorSViewHolder  holder, int position) {
        //Get the current sport
        Doctors currentDoctor = mDoctors.get(position);
        //Bind the data to the views
        holder.bindTo(currentDoctor);
    }


    @Override
    public int getItemCount() {
        return mDoctors.size();
    }

    static class DoctorSViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        //Member Variables for the holder data
        public TextView mNameText, mPlaceText,mSpecialiteText;
        ImageView mDoctorImage;
        Context mCont;
        Doctors mCurrentDoctor;
        GradientDrawable mGradientDrawable;

        DoctorSViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);
            //Initialize the views
            mNameText = (TextView) itemView.findViewById(R.id.name_doctor);
            mPlaceText = (TextView) itemView.findViewById(R.id.place_doctor) ;
            mDoctorImage = (ImageView) itemView.findViewById(R.id.doctor_image);
            mSpecialiteText =(TextView) itemView.findViewById(R.id.specialite_doctor);
            mCont = context;
            mGradientDrawable = gradientDrawable;
            //Set the OnClickListener to the whole view
            itemView.setOnClickListener(this);
        }

        void bindTo(Doctors currentDoctor) {
            //Populate the textviews with data
            mNameText.setText(currentDoctor.getNameDoctor());
            mPlaceText.setText(currentDoctor.getPlaceDoctor());
            mSpecialiteText.setText(currentDoctor.getSpec());
            //Get the current sport
            mCurrentDoctor = currentDoctor;
            if(mCurrentDoctor.getSexDoctor().equals("man")) Glide.with(mCont).load(R.drawable.doctorm).placeholder(mGradientDrawable).into(mDoctorImage);
            else Glide.with(mCont).load(R.drawable.doctorf).placeholder(mGradientDrawable).into(mDoctorImage);
        }

        @Override
        public void onClick(View view) {
            FirebaseUser user1= FirebaseAuth.getInstance().getInstance().getCurrentUser();
            if (!user1.getUid().equals(mCurrentDoctor.getDoctor_ID_Firebase()) ){
            Intent intent = Doctors.starter(mCont, mCurrentDoctor.getDoctor_ID_Firebase(), mCurrentDoctor.getNameDoctor());
            mCont.startActivity(intent);
        }else {
            Toast.makeText(mCont,"you cant send to your self",Toast.LENGTH_LONG).show();
        }
        }
    }
}
