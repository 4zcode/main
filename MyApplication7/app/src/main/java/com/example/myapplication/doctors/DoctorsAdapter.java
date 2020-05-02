package com.example.myapplication.doctors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorSViewHolder> {

    private GradientDrawable mGradientDrawable;
    private ArrayList<Doctors> mDoctors;
    private Context mContext;
    private ArrayList<Doctors> mDoctorArray = new ArrayList<>();

    DoctorsAdapter(Context context, ArrayList<Doctors> doctorData) {
        this.mDoctors = doctorData;
        this.mContext = context;
        this.mDoctorArray.addAll(doctorData);
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);
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
        Doctors currentDoctor = mDoctors.get(position);
        holder.bindTo(currentDoctor);
    }


    @Override
    public int getItemCount() {
        return mDoctors.size();
    }

    static class DoctorSViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mNameText, mPlaceText,mSpecialiteText;
        private ImageView mDoctorImage;
        private Context mCont;
        private Doctors mCurrentDoctor;
        private GradientDrawable mGradientDrawable;
        private SharedPreferences myPef;


        DoctorSViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);
            mNameText = (TextView) itemView.findViewById(R.id.name_doctor);
            mPlaceText = (TextView) itemView.findViewById(R.id.place_doctor) ;
            mDoctorImage = (ImageView) itemView.findViewById(R.id.doctor_image);
            mSpecialiteText =(TextView) itemView.findViewById(R.id.specialite_doctor);
            mCont = context;
            mGradientDrawable = gradientDrawable;
            itemView.setOnClickListener(this);
        }

        void bindTo(Doctors currentDoctor) {
            mNameText.setText(currentDoctor.getNameDoctor());
            mPlaceText.setText(currentDoctor.getPlaceDoctor());
            mSpecialiteText.setText(currentDoctor.getSpec());
            mCurrentDoctor = currentDoctor;
            if (isNetworkAvailable()) {
                Picasso.with(mCont).load(mCurrentDoctor.getImageUrl()).into(mDoctorImage);
            }else{
                if(mCurrentDoctor.getSexDoctor().equals("man")) Glide.with(mCont).load(R.drawable.doctorm).placeholder(mGradientDrawable).into(mDoctorImage);
                else Glide.with(mCont).load(R.drawable.doctorf).placeholder(mGradientDrawable).into(mDoctorImage);
            }
        }
        public boolean isNetworkAvailable() {
            boolean HaveConnectWIFI = false;
            boolean HaveConnectMobile = false;

            ConnectivityManager connectivityManager = (ConnectivityManager) mCont.getSystemService(mCont.CONNECTIVITY_SERVICE);
            NetworkInfo[] activeNetworkInfo = connectivityManager.getAllNetworkInfo();
            for (NetworkInfo ni : activeNetworkInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        HaveConnectWIFI = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        HaveConnectMobile = true;
            }
            return HaveConnectMobile || HaveConnectWIFI;
        }

        @Override
        public void onClick(View view) {
            myPef =mCont.getSharedPreferences("userPref", Context.MODE_PRIVATE);
            if (myPef.getBoolean("IsLogIn", false) && FirebaseAuth.getInstance().getCurrentUser() != null) {
                FirebaseUser user1 = FirebaseAuth.getInstance().getInstance().getCurrentUser();
                if (!user1.getUid().equals(mCurrentDoctor.getDoctor_ID_Firebase())) {
                    Intent intent = Doctors.starter(mCont, mCurrentDoctor.getDoctor_ID_Firebase(), mCurrentDoctor.getNameDoctor());
                    mCont.startActivity(intent);
                } else {
                    Toast.makeText(mCont, "you cant send to your self", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
