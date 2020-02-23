package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class adapterpharmacies extends RecyclerView.Adapter<adapterpharmacies.viewholder> {
  public  ArrayList<pharmaciesinit> liste;
  public Context context;
 public GradientDrawable gradientDrawable;
 public  adapterpharmacies (Context context1,ArrayList liste1){
     this.context=context1;
     this.liste=liste1;
     gradientDrawable=new GradientDrawable();
     gradientDrawable.setColor(Color.BLUE);
     Drawable drawable = ContextCompat.getDrawable(context,R.drawable.img_badminton);
     if(drawable!=null){gradientDrawable.setSize(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());}
 }

    @NonNull
    @Override
    public viewholder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.pharmaciesinit,parent,false);
        return new viewholder(context,view,gradientDrawable);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        pharmaciesinit mcarent=liste.get(position);
        holder.onbind(mcarent);

    }

    @Override
    public int getItemCount() {
        return liste.size();
    }


    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView1,textView2,textView3,textView4;
        ImageView imageView;
        Context mcontextm;
        pharmaciesinit mword;
        GradientDrawable mgd;
        public viewholder(@NonNull Context contextm, View itemView,GradientDrawable mgradientDrawable) {
            super(itemView);
            mcontextm = contextm;
            imageView=( ImageView) itemView.findViewById(R.id.image);
            textView1=(TextView) itemView.findViewById(R.id.text1);
            textView2=(TextView) itemView.findViewById(R.id.text2);
            textView3=(TextView) itemView.findViewById(R.id.text3);
            textView4=(TextView) itemView.findViewById(R.id.text4);

            gradientDrawable=mgradientDrawable;
            itemView.setOnClickListener(this);
        }
        public void onbind(pharmaciesinit ilyes){
            textView1.setText(ilyes.getThename());
            textView2.setText(ilyes.getTheadress());
            textView3.setText(ilyes.getOppen());
            textView4.setText(ilyes.getClose());
            mword=ilyes;
            Glide.with(context).load(ilyes.getIm()).placeholder(gradientDrawable).into(imageView);
        }


        @Override
        public void onClick(View v) {

        }
    }
}
