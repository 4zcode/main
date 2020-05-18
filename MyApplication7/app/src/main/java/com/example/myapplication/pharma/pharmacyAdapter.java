package com.example.myapplication.pharma;

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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class pharmacyAdapter extends RecyclerView.Adapter<pharmacyAdapter.pharmaciesViewHolder> {
    private GradientDrawable mGradientDrawable;
    private ArrayList<pharmacy> mpharmacy;
    private Context mContext;
    private ArrayList<pharmacy> mpharmacyArray = new ArrayList<>();

    pharmacyAdapter(Context context, ArrayList<pharmacy> pharmacyData) {
        this.mpharmacy = pharmacyData;
        this.mContext = context;
        this.mpharmacyArray.addAll(pharmacyData);
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);
        Drawable drawable = ContextCompat.getDrawable
                (mContext, R.drawable.phlogo);
        if (drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }
    public void filter(String text) {
        if(text.isEmpty()){
            mpharmacy.clear();
            mpharmacy.addAll(mpharmacyArray);
        } else{
            ArrayList<pharmacy> result = new ArrayList<>();
            text = text.toLowerCase();
            for(pharmacy item: mpharmacyArray){
                if(item.getThename().toLowerCase().contains(text) ||
                        item.theadress.toLowerCase().contains(text)){
                    result.add(item);
                }
            }
            mpharmacy.clear();
            mpharmacy.addAll(result);
        }
        notifyDataSetChanged();
    }


    @Override
    public pharmacyAdapter.pharmaciesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pharmaciesinit, parent, false);
        return new pharmacyAdapter.pharmaciesViewHolder(mContext, view, mGradientDrawable);
    }


    @Override
    public void onBindViewHolder(pharmaciesViewHolder holder, int position) {
        pharmacy currentpharmacy = mpharmacy.get(position);
        holder.bindTo(currentpharmacy);
    }


    @Override
    public int getItemCount() {
        return mpharmacy.size();
    }

    static class pharmaciesViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mNameText, mPlaceText,mphone,mopen,mclose;
        private ImageView mpharmciesImage;
        private Context mCont;
        private pharmacy mCurrentpharmacies;
        private GradientDrawable mGradientDrawable;
        private SharedPreferences myPef;


        pharmaciesViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);
            mNameText = (TextView) itemView.findViewById(R.id.name_pharma);
            mPlaceText = (TextView) itemView.findViewById(R.id.place_pharma) ;
            mpharmciesImage = (ImageView) itemView.findViewById(R.id.pharma_image);
            mphone =(TextView) itemView.findViewById(R.id.phone_pharma);
            mopen =(TextView) itemView.findViewById(R.id.open_pharma);
            mclose =(TextView) itemView.findViewById(R.id.close_pharma);
            mCont = context;
            mGradientDrawable = gradientDrawable;
            itemView.setOnClickListener(this);
        }

        void bindTo(pharmacy Currentpharmacies) {
            mNameText.setText(Currentpharmacies.thename);
            mPlaceText.setText(Currentpharmacies.theadress);
            mphone.setText(Currentpharmacies.phone);
            mclose.setText(Currentpharmacies.close);
            mopen.setText(Currentpharmacies.oppen);
            mCurrentpharmacies = Currentpharmacies;
            if (isNetworkAvailable()) {
                Picasso.with(mCont).load(mCurrentpharmacies.getImageUrl()).into(mpharmciesImage);
            }else{
                Glide.with(mCont).load(R.drawable.phlogo).placeholder(mGradientDrawable).into(mpharmciesImage);
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
                if (!user1.getUid().equals(mCurrentpharmacies.getPHARMA_ID_Firebase())) {
                    Intent intent = pharmacy.starter(mCont,mCurrentpharmacies.getPHARMA_ID_Firebase(), mCurrentpharmacies.getThename());
                    mCont.startActivity(intent);
                } else {
                    Toast.makeText(mCont, "you cant send to your self", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}