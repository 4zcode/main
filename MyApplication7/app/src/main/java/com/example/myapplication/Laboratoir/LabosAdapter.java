package com.example.myapplication.Laboratoir;

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

class LabosAdapter extends RecyclerView.Adapter<LabosAdapter.LabosViewHolder> {
    private GradientDrawable mGradientDrawable;
    private ArrayList<Labo> mlabo;
    private Context mContext;
    private ArrayList<Labo> mLaboArray = new ArrayList<>();

    LabosAdapter(Context context, ArrayList<Labo> laboData) {
        this.mlabo = laboData;
        this.mContext = context;
        this.mLaboArray.addAll(laboData);
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
            mlabo.clear();
            mlabo.addAll(mLaboArray);
        } else{
            ArrayList<Labo> result = new ArrayList<>();
            text = text.toLowerCase();
            for(Labo item: mLaboArray){
                if(item.getLaboName().toLowerCase().contains(text) ||
                        item.getLaboPlace().toLowerCase().contains(text)){
                    result.add(item);
                }
            }
            mlabo.clear();
            mlabo.addAll(result);
        }
        notifyDataSetChanged();
    }


    @Override
    public LabosAdapter.LabosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.labo_item_view, parent, false);
        return new LabosAdapter.LabosViewHolder(mContext, view, mGradientDrawable);
    }


    @Override
    public void onBindViewHolder(LabosViewHolder holder, int position) {
        Labo currentlabo = mlabo.get(position);
        holder.bindTo(currentlabo);
    }


    @Override
    public int getItemCount() {
        return mlabo.size();
    }

    static class LabosViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mNameText, mPlaceText,mphone;
        private ImageView mlaboImage;
        private Context mCont;
        private Labo mCurrentlabo;
        private GradientDrawable mGradientDrawable;
        private SharedPreferences myPef;


        LabosViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);
            mNameText = (TextView) itemView.findViewById(R.id.labo_name);
            mPlaceText = (TextView) itemView.findViewById(R.id.labo_place) ;
            mlaboImage = (ImageView) itemView.findViewById(R.id.labo_image);
            mphone =(TextView) itemView.findViewById(R.id.labo_contact);
            mCont = context;
            mGradientDrawable = gradientDrawable;
            itemView.setOnClickListener(this);
        }

        void bindTo(Labo Currentlabo) {
            mNameText.setText(Currentlabo.laboName);
            mPlaceText.setText(Currentlabo.laboPlace);
            mphone.setText(Currentlabo.laboContact);
            mCurrentlabo = Currentlabo;
            if (isNetworkAvailable()) {
                Picasso.with(mCont).load(mCurrentlabo.getImageResource()).into(mlaboImage);
            }else{
                Glide.with(mCont).load(R.drawable.phlogo).placeholder(mGradientDrawable).into(mlaboImage);
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
                if (!user1.getUid().equals(mCurrentlabo.getLabo_ID_Firebase())) {
                    Intent intent = Labo.starter(mCont,mCurrentlabo.getLabo_ID_Firebase(), mCurrentlabo.getLaboName());
                    mCont.startActivity(intent);
                } else {
                    Toast.makeText(mCont, "you cant send to your self", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
