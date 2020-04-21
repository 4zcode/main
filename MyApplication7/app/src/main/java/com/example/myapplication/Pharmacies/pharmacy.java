package com.example.myapplication.Pharmacies;

public class pharmacy {
    String thename;
    String theadress;
    String oppen;
    String close;
    public pharmacy(String thenamem, String theadressm, String oppenm, String closem){
        this.close=closem;
        this.oppen=oppenm;
        this.theadress=theadressm;
        this.thename=thenamem;
    }
public String getThename(){return thename;}
public String getTheadress(){return theadress;}
public String getOppen(){return oppen;}
public String getClose(){return close;}
public void setThename(String n){thename=n;}
    public void setTheadress(String a){theadress=a;}
    public void setOppen(String o){oppen=o;}
    public void setClose(String c){close=c;}

}
