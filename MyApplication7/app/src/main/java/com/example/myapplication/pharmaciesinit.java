package com.example.myapplication;

public class pharmaciesinit {
    String thename;
    String theadress;
    String oppen;
    String close;
    int Im;
    public pharmaciesinit(String thenamem,String theadressm,String oppenm,String closem,int image){
        this.close=closem;
        this.oppen=oppenm;
        this.theadress=theadressm;
        this.thename=thenamem;
        this.Im=image;
    }
public String getThename(){return thename;}
public String getTheadress(){return theadress;}
public String getOppen(){return oppen;}
public String getClose(){return close;}
public int getIm(){return Im;}
public void setThename(String n){thename=n;}
    public void setTheadress(String a){theadress=a;}
    public void setOppen(String o){oppen=o;}
    public void setClose(String c){close=c;}
    public void setIm(int m){Im=m;}

}
