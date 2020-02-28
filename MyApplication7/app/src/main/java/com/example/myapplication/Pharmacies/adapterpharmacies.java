package com.example.myapplication.Pharmacies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

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
        }


        @Override
        public void onClick(View v) {
            String adrs =mword.getTheadress();
            Uri addressUri = Uri.parse("geo:0,0?q=" +adrs);
            Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);
            context.startActivity(intent);

        }
    }
}
