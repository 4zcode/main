package com.example.myapplication;

public class profil {
   private String fname;
   private String sname;
   private String adress;
   private String mail;
   private String phone;
   private String sex;
   private String mImageUrl;
   public profil(){

   }
   public profil(String fname, String sname, String adress, String mail, String phone, String sex , String mImageUrl){
       this.fname=fname;
       this.sname=sname;
       this.adress=adress;
       this.mail=mail;
       this.phone=phone;
       this.mImageUrl=mImageUrl;
       this.sex=sex;
   }


    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
