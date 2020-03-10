package com.example.myapplication.toolsbar.a_propos_de_nous;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class A_propos_de_nousModel extends ViewModel {

    private MutableLiveData<String> mText;

    public A_propos_de_nousModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is a_propos_de_nous fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}