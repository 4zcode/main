package com.example.myapplication.Hospital;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;


class HopitalsAdapter extends RecyclerView.Adapter<HopitalsAdapter.HopitalsViewHolder> {

    public GradientDrawable mGradientDrawable;
    public Cursor mHopitalsCursor;
    public static Context mContext;

    private final LayoutInflater mLayoutInflater;

    private int mHopitalPos;
    private int mHopitalFirabasePos;
    private int mHopitalNamePos;
    private int mHopitalPlacePos;
    private int mHopitalNumberPos;
    private int mHopitalImagePos;


    // private ArrayList<Hopital> mHopitalArray = new ArrayList<>();


    HopitalsAdapter(Context context, Cursor cursor) {
        this.mHopitalsCursor = cursor;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(this.mContext);
      //  this.mHopitalArray.addAll(mHopitalsData);
       populateColumnPositions();
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);
        Drawable drawable = ContextCompat.getDrawable
                (mContext, R.drawable.profile);
        if (drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }


    private void populateColumnPositions() {
        if(mHopitalsCursor == null)
            return;
        // Get column indexes from mCursor
        mHopitalPos = mHopitalsCursor.getColumnIndex(DatabaseHelper._ID);
        mHopitalFirabasePos = mHopitalsCursor.getColumnIndex(DatabaseHelper._ID_HOSPITAL_FIREBASE);
        mHopitalNamePos = mHopitalsCursor.getColumnIndex(DatabaseHelper.NAME__HOSPITAL);
        mHopitalPlacePos = mHopitalsCursor.getColumnIndex(DatabaseHelper.PLACE__HOSPITAL);
        mHopitalNumberPos = mHopitalsCursor.getColumnIndex(DatabaseHelper.NUMBER__HOSPITAL);
        mHopitalImagePos = mHopitalsCursor.getColumnIndex(DatabaseHelper.IMAGE_HOSPITAL_URL);

    }

    public void changeCursor(Cursor cursor) {
        if(mHopitalsCursor != null)
            mHopitalsCursor.close();
        mHopitalsCursor = cursor;
        populateColumnPositions();
        notifyDataSetChanged();
    }

   /* public void filter(String text) {
        if(text.isEmpty()){
            mHopitalsCursor.close();
        } else{
           // ArrayList<Hopital> result = new ArrayList<>();
            text = text.toLowerCase();

        }
        notifyDataSetChanged();
    }

    */


    @Override
    public HopitalsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.user_item, parent, false);
        return new HopitalsViewHolder(mContext, view, mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(HopitalsViewHolder holder, int position) {
        mHopitalsCursor.moveToPosition(position);

        Glide.with(mContext)
                .load(mHopitalsCursor.getString(mHopitalImagePos))
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(mGradientDrawable)
                .into(holder.mHopitalImage);

        String Name = mHopitalsCursor.getString(mHopitalNamePos);
        String Place = mHopitalsCursor.getString(mHopitalPlacePos);
        String Phone = mHopitalsCursor.getString(mHopitalNumberPos);

        int id = mHopitalsCursor.getInt(mHopitalPos);
        String FireBaseId =mHopitalsCursor.getString(mHopitalFirabasePos);


        holder.mHopitalNameTextView.setText(Name);
        holder.mHopitalPlaceTextView.setText(Place);
        holder.mHopitalContactTextView.setText(Phone);

        holder.mId = id;

    }


    @Override
    public int getItemCount() {
        return mHopitalsCursor == null ? 0 : mHopitalsCursor.getCount();
    }

    static class HopitalsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public TextView mHopitalNameTextView, mHopitalPlaceTextView, mHopitalContactTextView;
        public ImageView mHopitalImage;
        public int mId;
        Context mCont;
        GradientDrawable mGradientDrawable;

        HopitalsViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);
            mHopitalNameTextView = (TextView) itemView.findViewById(R.id.user_name);
            mHopitalPlaceTextView = (TextView) itemView.findViewById(R.id.user_place);
            mHopitalContactTextView = (TextView) itemView.findViewById(R.id.user_other);
            mHopitalImage = (ImageView) itemView.findViewById(R.id.user_image);
            mCont = context;
            mGradientDrawable = gradientDrawable;
            itemView.setOnClickListener(this);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View view) {
            Intent detailIntent = new Intent(mCont, HospitalProfileActivity.class);
            detailIntent.putExtra("id",mId);
            Pair<View, String> p1 = Pair.create((View)mHopitalImage,"HOPITALIMAGE");
            Pair<View, String> p2 = Pair.create((View)mHopitalContactTextView,"PHONE");
            Pair<View, String> p3 = Pair.create((View)mHopitalPlaceTextView,"LOCATION");
            ActivityOptions options = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                options = ActivityOptions.makeSceneTransitionAnimation((Activity) mCont, p1 ,p2,p3);
            }
            mCont.startActivity(detailIntent, options.toBundle());
        }
    }
}