package com.example.myapplication.doctors.advanceSearch;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.DetailsDoctorActivity;
import com.example.myapplication.Profiles.UserProfile;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorSViewHolder> {

    private GradientDrawable mGradientDrawable;
    private ArrayList<Doctors> mDoctors;
    private Context mContext;
    private ArrayList<Doctors> mDoctorArray = new ArrayList<>();

    public DoctorsAdapter(Context context, ArrayList<Doctors> doctorData) {
        this.mDoctors = doctorData;
        this.mContext = context;
        this.mDoctorArray.addAll(doctorData);
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);
        Drawable drawable = ContextCompat.getDrawable
                (mContext, R.drawable.doctorm);
        if (drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }
    public void filter(String text, String adresse, String speciality) {
        DBManagerDoctor db= new DBManagerDoctor(mContext);
        db.open();
        if(text.isEmpty()){
            mDoctors.clear();
            mDoctors.addAll(mDoctorArray);
        } else{
            ArrayList<Doctors> result = new ArrayList<>();
            text = text.toLowerCase();
            result= db.listdoctors(1,text,adresse,speciality);
            mDoctors.clear();
            mDoctors.addAll(result);
        }
        notifyDataSetChanged();
    }


    @Override
    public DoctorSViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.doctor_item, parent, false);
        return new DoctorSViewHolder(mContext, view, mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(DoctorSViewHolder  holder, int position) {
        Doctors currentDoctor = mDoctors.get(position);
        holder.bindTo(currentDoctor);
        Animation animation = AnimationUtils.loadAnimation(mContext,android.R.anim.fade_in);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return mDoctors.size();
    }

    static class DoctorSViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mNameText, mPlaceText,mSpecialiteText;
        private ImageView mDoctorImage;
        private Context mCont;
        private Doctors mCurrentDoctor;
        private GradientDrawable mGradientDrawable;
        private SharedPreferences myPef;
        private CardView backGroundLayout;

        DoctorSViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);
            mNameText = (TextView) itemView.findViewById(R.id.name_doctor);
            mPlaceText = (TextView) itemView.findViewById(R.id.place_doctor) ;
            mDoctorImage = (ImageView) itemView.findViewById(R.id.doctor_image);
            mSpecialiteText =(TextView) itemView.findViewById(R.id.specialite_doctor);
            backGroundLayout = (CardView) itemView.findViewById(R.id.doctor_cordinator_background);
            mCont = context;
            mGradientDrawable = gradientDrawable;
            itemView.setOnClickListener(this);
        }

        void bindTo(Doctors currentDoctor) {
            mNameText.setText(WordUtils.capitalizeFully(currentDoctor.getNameDoctor()));
            mPlaceText.setText(WordUtils.capitalizeFully(currentDoctor.getPlaceDoctor()));
            mSpecialiteText.setText(currentDoctor.getSpec());
            mCurrentDoctor = currentDoctor;
            Glide.with(mCont).load(R.drawable.profile)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(R.drawable.profile)
                    .into(mDoctorImage);
            backGroundLayout.setAnimation(AnimationUtils.loadAnimation(mCont,R.anim.fall_down));
            mDoctorImage.setAnimation(AnimationUtils.loadAnimation(mCont,R.anim.fall_down_retard));

        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View view) {
            myPef = mCont.getSharedPreferences("userPref", Context.MODE_PRIVATE);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (FirebaseAuth.getInstance().getCurrentUser() == null || !user.getUid().equals(mCurrentDoctor.getDoctor_ID_Firebase())) {
                    Intent intent = Doctors.starter(mCont, mCurrentDoctor.getDoctor_ID_Firebase(),mCurrentDoctor.getNameDoctor(),mCurrentDoctor.getImageUrl() ,mCurrentDoctor.getPlaceDoctor(),mCurrentDoctor.getService(),mCurrentDoctor.getSpec(),mCurrentDoctor.getPhone(),mCurrentDoctor.getTime());
                    Pair<View, String> p1 = Pair.create((View)mDoctorImage,"DOCTORIMAGE");
                    Pair<View, String> p2 = Pair.create((View)mNameText,"DOCTORNAME");
                    ActivityOptions options = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        options = ActivityOptions.makeSceneTransitionAnimation((Activity) mCont, p1 ,p2);
                    }
                    mCont.startActivity(intent, options.toBundle());
                } else {
                    mCont.startActivity(new Intent(mCont, UserProfile.class));
                }
            }
        }
}
