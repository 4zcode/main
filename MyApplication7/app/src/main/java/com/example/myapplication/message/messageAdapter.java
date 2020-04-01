package com.example.myapplication.message;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.ArrayList;

import static com.example.myapplication.R.*;

public class messageAdapter extends RecyclerView.Adapter<messageAdapter.MessageViewHolder> {
    public GradientDrawable mGradientDrawable;
    public ArrayList<Message> mMessagesData;
    public Context mContext;
    public messageAdapter(Context context, ArrayList<Message> message){
        this.mMessagesData = message;
        this.mContext = context;
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);

        //Make the placeholder same size as the images
        Drawable drawable = ContextCompat.getDrawable
                (mContext, R.drawable.doctorm);
        if (drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }

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
        Message currentMessage = mMessagesData.get(position);

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
        public Message mCurrentMessage;
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
            itemView.setOnClickListener(this);        }

        @Override
        public void onClick(View v) {
            FirebaseUser user1= FirebaseAuth.getInstance().getInstance().getCurrentUser();
            if (!user1.getUid().equals(mCurrentMessage.getMessage_ID_Firebase()) ) {
                Intent intent = Message.starter(mCont, mCurrentMessage.getMessage_ID_Firebase(), mCurrentMessage.getSender());
                mCont.startActivity(intent);
            }
        }

        public void bindTo(Message currentMessage) {
            mCurrentMessage = currentMessage;
            mSenderNameTextView.setText(currentMessage.getSender());
            if (! currentMessage.Is_Readed()){
                mMessageTextView.setTextColor(Color.GRAY);
                mMessageTextView.setTypeface(Typeface.DEFAULT_BOLD);
            }
            mMessageTextView.setText(currentMessage.getMessage());
            String mydate = DateFormat.getDateTimeInstance().format(currentMessage.getDate());
            mDateTest.setText(mydate);
            Glide.with(mCont).load(drawable.doctorm).placeholder(mGradientDrawable).into(mSenderImage);

        }
    }
}
