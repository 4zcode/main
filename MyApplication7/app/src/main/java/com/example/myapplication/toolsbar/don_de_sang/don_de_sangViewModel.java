package com.example.myapplication.toolsbar.don_de_sang;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class don_de_sangViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public don_de_sangViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is don_de_sang fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}