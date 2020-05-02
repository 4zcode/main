package com.example.myapplication.Hospital;

import android.content.Context;
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
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;

import java.util.ArrayList;

class HopitalsAdapter extends RecyclerView.Adapter<HopitalsAdapter.HopitalsViewHolder> {

    public GradientDrawable mGradientDrawable;
    public ArrayList<Hopital> mHopitalsData;
    public static Context mContext;
    private ArrayList<Hopital> mHopitalArray = new ArrayList<>();


    HopitalsAdapter(Context context, ArrayList<Hopital> HopitalsData) {
        this.mHopitalsData = HopitalsData;
        this.mContext = context;
        this.mHopitalArray.addAll(mHopitalsData);
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
            mHopitalsData.clear();
            mHopitalsData.addAll(mHopitalArray);
        } else{
            ArrayList<Hopital> result = new ArrayList<>();
            text = text.toLowerCase();
            for(Hopital item: mHopitalArray){
                if(item.getHopitalName().toLowerCase().contains(text) ||
                        item.getHopitalPlace().toLowerCase().contains(text)){
                    result.add(item);
                }
            }
            mHopitalsData.clear();
            mHopitalsData.addAll(result);
        }
        notifyDataSetChanged();
    }


    @Override
    public HopitalsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.hospital_item_view, parent, false);
        return new HopitalsViewHolder(mContext, view, mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(HopitalsViewHolder holder, int position) {
        Hopital currentHopital = mHopitalsData.get(position);
        holder.bindTo(currentHopital);
    }


    @Override
    public int getItemCount() {
        return mHopitalsData.size();
    }

    static class HopitalsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public TextView mHopitalNameTextView;
        public TextView mHopitalPlaceTextView;
        public TextView mHopitalContactTextView;
        ImageView mHopitalImage;
        Context mCont;
        Hopital mCurrentHopital;
        GradientDrawable mGradientDrawable;

        HopitalsViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);
            mHopitalNameTextView = (TextView) itemView.findViewById(R.id.hopital_name);
            mHopitalPlaceTextView = (TextView) itemView.findViewById(R.id.hopital_place);
            mHopitalContactTextView = (TextView) itemView.findViewById(R.id.hopital_contact);
            mHopitalImage = (ImageView) itemView.findViewById(R.id.hopital_image);
            mCont = context;
            mGradientDrawable = gradientDrawable;
            itemView.setOnClickListener(this);
        }

        void bindTo(Hopital currentHopital) {
            mHopitalNameTextView.setText(currentHopital.getHopitalName());
            mHopitalPlaceTextView.setText(currentHopital.getHopitalPlace());
            mHopitalContactTextView.setText(currentHopital.getHopitalContact());
            mCurrentHopital = currentHopital;
            Glide.with(mCont)
                    .load(currentHopital.getImageResource())
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(mGradientDrawable)
                    .into(mHopitalImage);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mCont,"Clicked",Toast.LENGTH_SHORT).show();
        }
    }
}
