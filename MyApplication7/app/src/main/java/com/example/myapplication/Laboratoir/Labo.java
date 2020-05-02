package com.example.myapplication.Laboratoir;

import android.content.Context;
import android.content.Intent;

import com.example.myapplication.message.chatRoom;

public class Labo {
        public String labo_ID_Firebase;
        public String laboName;
        public String laboPlace;
        public String laboContact;
        public String imageResource;
        public static final String RECIVER = "Reciver";
        public static final String SENDER = "sender";


        public Labo(String labo_ID_Firebase, String name, String place, String contact, String laboImage) {
            this.labo_ID_Firebase = labo_ID_Firebase;
            this.laboName = name;
            this.laboPlace = place;
            this.laboContact = contact;
            this.imageResource = laboImage;
        }


        public String getLaboName() {
            return laboName;
        }

        public String getLaboPlace() {
            return laboPlace;
        }

        public String getLaboContact() {
            return laboContact;
        }

        public String getImageResource() {
            return imageResource;
        }

        public String getLabo_ID_Firebase() {
            return labo_ID_Firebase;
        }

        static Intent starter(Context context, String reciver, String sender) {
            Intent chatIntent = new Intent(context, chatRoom.class);
            chatIntent.putExtra(RECIVER, reciver);
            chatIntent.putExtra(SENDER, sender);
            return chatIntent;
        }

}
