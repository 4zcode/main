package com.example.myapplication.Drugs;

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

class MedicamentsAdapter extends RecyclerView.Adapter<MedicamentsAdapter.MedicamentViewHolder>  {

    //Member variables
    public GradientDrawable mGradientDrawable;
    public ArrayList<Medicament> mMedicamentData;
    public static Context mContext;

    MedicamentsAdapter(Context context, ArrayList<Medicament> medicamntData) {
        this.mMedicamentData = medicamntData;
        this.mContext = context;

        //Prepare gray placeholder
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);

        //Make the placeholder same size as the images
        Drawable drawable = ContextCompat.getDrawable
                (mContext, R.drawable.doctorm);
        if(drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }



    @Override
    public MedicamentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.medicament_list_item,parent,false);
        return new MedicamentViewHolder(mContext,view, mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(MedicamentViewHolder holder, int position) {

        //Get the current sport
        Medicament currentMedicament = mMedicamentData.get(position);

        //Bind the data to the views
        holder.bindTo(currentMedicament);

    }


    @Override
    public int getItemCount() {
        return mMedicamentData.size();
    }

    static class MedicamentViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        //Member Variables for the holder data
        public TextView mNameMedicamentTextView;
        public TextView mPrixMedicamentTextView;
        public TextView mClassMedicamentTextView;
        ImageView mMedicamentImage;
        Context mCont;
        Medicament mCurrentMedicament;
        GradientDrawable mGradientDrawable;

        MedicamentViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);

            //Initialize the views
            mNameMedicamentTextView = (TextView)itemView.findViewById(R.id.medicament_name);
            mClassMedicamentTextView = (TextView)itemView.findViewById(R.id.medicament_class);
            mPrixMedicamentTextView=(TextView)itemView.findViewById(R.id.medicament_prix);
            mMedicamentImage = (ImageView)itemView.findViewById(R.id.medicament_image);

            mCont = context;
            mGradientDrawable = gradientDrawable;

            //Set the OnClickListener to the whole view
            itemView.setOnClickListener(this);
        }

        void bindTo(Medicament currentMedicament){
            //Populate the textviews with data
            mNameMedicamentTextView.setText(currentMedicament.getMedicamenName());
            mClassMedicamentTextView.setText(currentMedicament.getMedicamentClass());
            mPrixMedicamentTextView.setText(currentMedicament.getMedicamenPrix());

            //Get the current sport
            mCurrentMedicament = currentMedicament;

            Glide.with(mCont).load(R.drawable.medicament).placeholder(mGradientDrawable).into(mMedicamentImage);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
