package com.example.myapplication.Laboratoir;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
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

class LabosAdapter extends RecyclerView.Adapter<LabosAdapter.LabosViewHolder> {

    //Member variables
    public GradientDrawable mGradientDrawable;
    public ArrayList<Labo> mLabodata ;
    public static Context mContext;

    LabosAdapter(Context context, ArrayList<Labo> LaboData) {
        this.mLabodata = LaboData;
        this.mContext = context;

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


    @Override
    public LabosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.labo_item_view, parent, false);
        return new LabosViewHolder(mContext, view, mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(LabosViewHolder holder, int position) {

        //Get the current sport
        Labo currentLabo = mLabodata.get(position);

        //Bind the data to the views
        holder.bindTo(currentLabo);

    }


    @Override
    public int getItemCount() {
        return mLabodata.size();
    }

    static class LabosViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        //Member Variables for the holder data
        public TextView mLaboNameTextView;
        public TextView mLaboPlaceTextView;
        public TextView mLaboContactTextView;
        ImageView mLaboImage;
        Context mCont;
        Labo mCurrentLabo;
        GradientDrawable mGradientDrawable;

        LabosViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);

            //Initialize the views
            mLaboNameTextView = (TextView) itemView.findViewById(R.id.labo_name);
            mLaboPlaceTextView = (TextView) itemView.findViewById(R.id.labo_place);
            mLaboContactTextView = (TextView) itemView.findViewById(R.id.labo_contact);
            mLaboImage = (ImageView) itemView.findViewById(R.id.labo_image);

            mCont = context;
            mGradientDrawable = gradientDrawable;

            //Set the OnClickListener to the whole view
            itemView.setOnClickListener(this);
        }

        void bindTo(Labo currentLabo) {
            //Populate the textviews with data
            mLaboNameTextView.setText(currentLabo.getLaboName());
            mLaboPlaceTextView.setText(currentLabo.getLaboPlace());
            mLaboContactTextView.setText(currentLabo.getLaboContact());

            //Get the current sport
            mCurrentLabo = currentLabo;

            Glide.with(mCont).load(currentLabo.getImageResource()).placeholder(mGradientDrawable).into(mLaboImage);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
