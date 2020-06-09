package com.example.myapplication.don_sang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

public class DonateurAdapter extends RecyclerView.Adapter<DonateurAdapter.DonateursViewHolder> {
    private GradientDrawable mGradientDrawable;
    private ArrayList<don_de_song> mdonateur;
    private Context mContext;
    private ArrayList<don_de_song> mdonateurArray = new ArrayList<>();

    DonateurAdapter(Context context, ArrayList<don_de_song> laboData) {
        this.mdonateur = laboData;
        this.mContext = context;
        this.mdonateurArray.addAll(laboData);
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);
        Drawable drawable = ContextCompat.getDrawable
                (mContext, R.drawable.blood);
        if (drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }
    public void filter(String text) {
        if(text.isEmpty()){
            mdonateur.clear();
            mdonateur.addAll(mdonateurArray);
        } else{
            ArrayList<don_de_song> result = new ArrayList<>();
            text = text.toLowerCase();
            for(don_de_song item: mdonateurArray){
                if(item.getGrsanguin().contains(text) ||
                        item.getAdressd().toLowerCase().contains(text)){
                    result.add(item);
                }
            }
            mdonateur.clear();
            mdonateur.addAll(result);
        }
        notifyDataSetChanged();
    }


    @Override
    public DonateurAdapter.DonateursViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.donateur_item, parent, false);
        return new DonateurAdapter.DonateursViewHolder(mContext, view, mGradientDrawable);
    }


    @Override
    public void onBindViewHolder(DonateursViewHolder holder, int position) {
        don_de_song currentdonateur = mdonateur.get(position);
        holder.bindTo(currentdonateur);
    }


    @Override
    public int getItemCount() {
        return mdonateur.size();
    }

    static class DonateursViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mNameText, mage,grsnaguin;
        private ImageView mDonateurImage;
        private Context mCont;
        private don_de_song mCurrentdonateur;
        private GradientDrawable mGradientDrawable;
        private SharedPreferences myPef;


        DonateursViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);
            mNameText = (TextView) itemView.findViewById(R.id.donateur_name);
            mage = (TextView) itemView.findViewById(R.id.agedonateur) ;
            mDonateurImage = (ImageView) itemView.findViewById(R.id.donateur_imageinit);
            grsnaguin =(TextView) itemView.findViewById(R.id.donateur_grsanguin);
            mCont = context;
            mGradientDrawable = gradientDrawable;
            itemView.setOnClickListener(this);
        }

        void bindTo(don_de_song Currentdonateur) {
            mNameText.setText(Currentdonateur.getFullname());
            mage.setText(Currentdonateur.getAge()+" years old");
            grsnaguin.setText((Currentdonateur.getGrsanguin()));
            mCurrentdonateur = Currentdonateur;
            if (isNetworkAvailable()) {
                Glide.with(mCont).load(mCurrentdonateur.getImaged())
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(mGradientDrawable)
                        .into(mDonateurImage);
            }else{
                Glide.with(mCont).load(R.drawable.blood).placeholder(mGradientDrawable).into(mDonateurImage);
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
          Intent intent=new Intent(mCont,donateurinfo.class);
          intent.putExtra("name_donateur",mCurrentdonateur.getFullname());
          intent.putExtra("adress_donateur",mCurrentdonateur.getAdressd());
          intent.putExtra("age_donateur",mCurrentdonateur.getAge());
          intent.putExtra("idfirebase_donateur",mCurrentdonateur.get_ID_firebase());
          intent.putExtra("image_donateur",mCurrentdonateur.getImaged());
          intent.putExtra("phone_donateur",mCurrentdonateur.getContact());
          intent.putExtra("grsanguin_donateur",mCurrentdonateur.getGrsanguin());
          mCont.startActivity(intent);

        }
    }
}
