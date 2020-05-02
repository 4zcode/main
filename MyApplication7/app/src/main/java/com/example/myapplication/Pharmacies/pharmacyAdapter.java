package com.example.myapplication.Pharmacies;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.R;

import java.util.ArrayList;

public class pharmacyAdapter extends RecyclerView.Adapter<pharmacyAdapter.viewholder> {
  private   ArrayList<pharmacy> pharmacyData;
  private Context context;
  private GradientDrawable gradientDrawable;
  private ArrayList<pharmacy> mPharmacyArray = new ArrayList<>();


    public pharmacyAdapter(Context context1, ArrayList liste){
     this.context=context1;
     this.pharmacyData=liste;
     this.mPharmacyArray.addAll(pharmacyData);

        gradientDrawable=new GradientDrawable();
     gradientDrawable.setColor(Color.BLUE);
 }
    public void filter(String text) {
        if(text.isEmpty()){
            pharmacyData.clear();
            pharmacyData.addAll(mPharmacyArray);
        } else{
            ArrayList<pharmacy> result = new ArrayList<>();
            text = text.toLowerCase();
            for(pharmacy item: mPharmacyArray){
                if(item.getThename().toLowerCase().contains(text) ||
                        item.getTheadress().toLowerCase().contains(text)){
                    result.add(item);
                }
            }
            pharmacyData.clear();
            pharmacyData.addAll(result);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.pharmacy_item,parent,false);
        return new viewholder(context,view,gradientDrawable);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        pharmacy mcarent=pharmacyData.get(position);
        holder.onbind(mcarent);

    }

    @Override
    public int getItemCount() {
        return pharmacyData.size();
    }


    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView NameTextView,PlaceTextView,NumberTextView,OpenTextView;
        private ImageView PharmacyImageView;
        private Context mcontextm;
        private pharmacy mCurrentPharmacy;
        private GradientDrawable mgd;

        public viewholder(@NonNull Context contextm, View itemView,GradientDrawable mgradientDrawable) {
            super(itemView);
            mcontextm = contextm;
            PharmacyImageView=( ImageView) itemView.findViewById(R.id.pharmacy_image);
            NameTextView=(TextView) itemView.findViewById(R.id.pharmacy_name);
            PlaceTextView=(TextView) itemView.findViewById(R.id.pharmacy_place);
            NumberTextView=(TextView) itemView.findViewById(R.id.pharmacy_number);
          //  OpenTextView=(TextView) itemView.findViewById(R.id.pharmacies_open);

            gradientDrawable=mgradientDrawable;
            itemView.setOnClickListener(this);
        }
        public void onbind(pharmacy currentPharmacy){
            NameTextView.setText(currentPharmacy.getThename());
            PlaceTextView.setText(currentPharmacy.getTheadress());
            NumberTextView.setText(currentPharmacy.getPhone());
           // OpenTextView.setText(currentPharmacy.getClose());
            mCurrentPharmacy=currentPharmacy;
            Glide.with(mcontextm)
                    .load(mCurrentPharmacy.ImageUrl)
                    .placeholder(R.drawable.doctorm)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(PharmacyImageView);
        }


        @Override
        public void onClick(View v) {
            Toast.makeText(mcontextm,"Clicked",Toast.LENGTH_SHORT).show();

        }
    }
}
