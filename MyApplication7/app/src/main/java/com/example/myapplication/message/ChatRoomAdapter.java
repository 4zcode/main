package com.example.myapplication.message;

import android.content.Context;
import android.graphics.Color;
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
import com.example.myapplication.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.myapplication.R.*;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.MessageViewHolder> {

    public final static String TAG = ChatRoomAdapter.class.getSimpleName();

    public GradientDrawable mGradientDrawable;
    public ArrayList<MessageChatItem> mMessagesData;
    public Context mContext;
    public ChatRoomAdapter(Context context, ArrayList<MessageChatItem> message){
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

        View view = LayoutInflater.from(mContext).inflate(layout.chat_room_message_item, parent, false);
        return new ChatRoomAdapter.MessageViewHolder(mContext, view, mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        //Get the current sport
        MessageChatItem currentMessage = mMessagesData.get(position);
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
        public MessageChatItem mCurrentMessage;
        public GradientDrawable mGradientDrawable;
        public MessageViewHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);

            //Initialize the views
            mSenderNameTextView = (TextView) itemView.findViewById(id.name_message_chat_sender);
            mMessageTextView = (TextView) itemView.findViewById(id.message_chat_text);
            mSenderImage = (ImageView) itemView.findViewById(id.message_chat_image);
            mDateTest = (TextView) itemView.findViewById(id.message_chat_date);

            mCont = context;
            mGradientDrawable = gradientDrawable;

            //Set the OnClickListener to the whole view
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
               Toast.makeText(mCont,"clicked",Toast.LENGTH_SHORT).show();

        }

        public void bindTo(MessageChatItem currentMessage) {

            mCurrentMessage = currentMessage;
            mSenderNameTextView.setText(currentMessage.getMsgName());

            mMessageTextView.setText(currentMessage.getMessage());
            String mydate = DateFormat.getDateTimeInstance().format(currentMessage.getDate());
            mDateTest.setText(mydate);


                Glide.with(mCont).load(mCurrentMessage.getImageResource())
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(mGradientDrawable)
                        .into(mSenderImage);




        }
    }
}
