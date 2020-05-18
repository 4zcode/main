package com.example.myapplication.pharma;

import android.content.Context;
import android.content.Intent;

import com.example.myapplication.message.chatRoom;

public class pharmacy {
    String PHARMA_ID_Firebase;
    String thename;
    String theadress;
    String phone;
    String oppen;
    String close;
    String ImageUrl;
    public static final String RECIVER = "Reciver";
    public static final String SENDER = "sender";

    public pharmacy() {
    }

    public pharmacy(String id_firebase, String thenamem, String theadressm, String phone, String oppenm, String closem, String image){
        this.PHARMA_ID_Firebase=id_firebase;
        this.close=closem;
        this.oppen=oppenm;
        this.theadress=theadressm;
        this.thename=thenamem;
        this.phone=phone;
        this.ImageUrl = image;
    }
    public String getThename(){return thename;}
    public String getTheadress(){return theadress;}
    public String getOppen(){return oppen;}
    public String getClose(){return close;}
    public void setThename(String n){thename=n;}
    public void setTheadress(String a){theadress=a;}
    public void setOppen(String o){oppen=o;}
    public void setClose(String c){close=c;}
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone){this.phone=phone;}



    public String getPHARMA_ID_Firebase() {
        return PHARMA_ID_Firebase;
    }

    public void setPHARMA_ID_Firebase(String PHARMA_ID_Firebase) {
        this.PHARMA_ID_Firebase = PHARMA_ID_Firebase;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    static Intent starter(Context context, String reciver, String sender) {
        Intent chatIntent = new Intent(context, chatRoom.class);
        chatIntent.putExtra(RECIVER, reciver);
        chatIntent.putExtra(SENDER, sender);
        return chatIntent;
    }

}