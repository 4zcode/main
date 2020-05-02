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
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.R;

import java.util.ArrayList;

class LabosAdapter extends RecyclerView.Adapter<LabosAdapter.LabosViewHolder> {

    //Member variables
    private GradientDrawable mGradientDrawable;
    private ArrayList<Labo> mlabo;
    private Context mContext;
    private ArrayList<Labo> mLaboArray = new ArrayList<>();

    LabosAdapter(Context context, ArrayList<Labo> laboData) {
        this.mlabo = laboData;
        this.mContext = context;
        this.mLaboArray.addAll(laboData);

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
    public LabosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.labo_item_view, parent, false);
        return new LabosViewHolder(mContext, view, mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(LabosViewHolder holder, int position) {

        //Get the current sport
        Labo currentLabo = mlabo.get(position);

        //Bind the data to the views
        holder.bindTo(currentLabo);

    }


    @Override
    public int getItemCount() {
        return mlabo.size();
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

            Glide.with(mCont)
                    .load(mCurrentLabo.getImageResource())
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(mGradientDrawable)
                    .into(mLaboImage);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mCont, "clicked", Toast.LENGTH_SHORT).show();
        }
    }
}
