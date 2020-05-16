package com.example.myapplication.message;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.ArrayList;

import static com.example.myapplication.R.drawable;
import static com.example.myapplication.R.id;
import static com.example.myapplication.R.layout;

public class messageAdapter extends RecyclerView.Adapter<messageAdapter.MessageViewHolder> {

    public final static String TAG = messageAdapter.class.getSimpleName();

    public GradientDrawable mGradientDrawable;
    public ArrayList<MessageItem> mMessagesData;
    public Context mContext;
    public messageAdapter(Context context, ArrayList<MessageItem> message){
        this.mMessagesData = message;
        this.mContext = context;
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);

    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(layout.message_item, parent, false);
        return new messageAdapter.MessageViewHolder(mContext, view, mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        //Get the current sport
        MessageItem currentMessage = mMessagesData.get(position);

        //Bind the data to the views
        holder.bindTo(currentMessage);
    }

    @Override
    public int getItemCount() {
        return mMessagesData.size();    }

        class MessageViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView mSenderNameTextView;
        public TextView mMessageTextView, mDateTest;
        public ImageView mSenderImage;
        public Context mCont;
        public MessageItem mCurrentMessage;
        public GradientDrawable mGradientDrawable;
        public MessageViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);

            //Initialize the views
            mSenderNameTextView = (TextView) itemView.findViewById(id.name_message_sender);
            mMessageTextView = (TextView) itemView.findViewById(id.messageText);
            mSenderImage = (ImageView) itemView.findViewById(id.sender_image);
            mDateTest = (TextView) itemView.findViewById(id.dateTest);

            mCont = context;
            mGradientDrawable = gradientDrawable;
            //Set the OnClickListener to the whole view
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            FirebaseUser user= FirebaseAuth.getInstance().getInstance().getCurrentUser();
            try {
               if (!user.getUid().equals(mCurrentMessage.getMessage_ID_Firebase()) ) {
                   Intent intent = MessageItem.starter(mCont, mCurrentMessage.getMessage_ID_Firebase(), mCurrentMessage.getSender(), mCurrentMessage.getImageResource());
                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  mCont.startActivity(intent);
               }
           }catch (Exception e){
               Log.d(TAG,"error : "+e.getMessage());
               e.printStackTrace();
               Toast.makeText(mCont,"clicked",Toast.LENGTH_SHORT).show();

           }


        }

        public void bindTo(MessageItem currentMessage) {
            mCurrentMessage = currentMessage;
            mSenderNameTextView.setText(currentMessage.getSender());
            if ( currentMessage.Is_Readed()){
                mMessageTextView.setTextColor(Color.GRAY);
                mMessageTextView.setTypeface(Typeface.DEFAULT_BOLD);
            }
            mMessageTextView.setText(currentMessage.getRecent_message());
            String mydate = DateFormat.getDateTimeInstance().format(currentMessage.getDate());
            mDateTest.setText(mydate);
            if (!mCurrentMessage.getImageResource().equals("R.drawable.doctorm")) {
                Glide.with(mCont).load(mCurrentMessage.getImageResource())
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(mGradientDrawable)
                        .into(mSenderImage);
            }else {
                mSenderImage.setImageResource(drawable.doctorm);
            }

        }
    }
}
