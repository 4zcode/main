package com.example.myapplication.Hospital;

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

class HopitalsAdapter extends RecyclerView.Adapter<HopitalsAdapter.HopitalsViewHolder> {

    private GradientDrawable mGradientDrawable;
    private ArrayList<Hopital> mhospital;
    private Context mContext;
    private ArrayList<Hopital> mhospitalArray = new ArrayList<>();

    HopitalsAdapter(Context context, ArrayList<Hopital> laboData) {
        this.mhospital = laboData;
        this.mContext = context;
        this.mhospitalArray.addAll(laboData);
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);
        Drawable drawable = ContextCompat.getDrawable
                (mContext, R.drawable.labologo);
        if (drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }
    public void filter(String text) {
        if(text.isEmpty()){
            mhospital.clear();
            mhospital.addAll(mhospitalArray);
        } else{
            ArrayList<Hopital> result = new ArrayList<>();
            text = text.toLowerCase();
            for(Hopital item: mhospitalArray){
                if(item.getHopitalName().toLowerCase().contains(text) ||
                        item.getHopitalPlace().toLowerCase().contains(text)){
                    result.add(item);
                }
            }
            mhospital.clear();
            mhospital.addAll(result);
        }
        notifyDataSetChanged();
    }


    @Override
    public HopitalsAdapter.HopitalsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.hospital_item_view, parent, false);
        return new HopitalsAdapter.HopitalsViewHolder(mContext, view, mGradientDrawable);
    }


    @Override
    public void onBindViewHolder(HopitalsViewHolder holder, int position) {
        Hopital currenthopital = mhospital.get(position);
        holder.bindTo(currenthopital);
    }


    @Override
    public int getItemCount() {
        return mhospital.size();
    }

    static class HopitalsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mNameText, mPlaceText,mphone;
        private ImageView mhopitalImage;
        private Context mCont;
        private Hopital mCurrenthopital;
        private GradientDrawable mGradientDrawable;
        private SharedPreferences myPef;


        HopitalsViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);
            mNameText = (TextView) itemView.findViewById(R.id.hopital_name);
            mPlaceText = (TextView) itemView.findViewById(R.id.hopital_place) ;
            mhopitalImage = (ImageView) itemView.findViewById(R.id.hopital_image);
            mphone =(TextView) itemView.findViewById(R.id.hopital_contact);
            mCont = context;
            mGradientDrawable = gradientDrawable;
            itemView.setOnClickListener(this);
        }

        void bindTo(Hopital Currenthopital) {
            mNameText.setText(Currenthopital.hopitalName);
            mPlaceText.setText(Currenthopital.hopitalPlace);
            mphone.setText(Currenthopital.hopitalContact);
            mCurrenthopital = Currenthopital;
            if (isNetworkAvailable()) {
                Picasso.with(mCont).load(mCurrenthopital.getImageResource()).into(mhopitalImage);
            }else{
                Glide.with(mCont).load(R.drawable.hospital).placeholder(mGradientDrawable).into(mhopitalImage);
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
                if (!user1.getUid().equals(mCurrenthopital.getHospital_ID_Firebase())) {
                    Intent intent = Hopital.starter(mCont,mCurrenthopital.getHospital_ID_Firebase(), mCurrenthopital.hopitalName);
                    mCont.startActivity(intent);
                } else {
                    Toast.makeText(mCont, "you cant send to your self", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
