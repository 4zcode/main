package com.example.myapplication.ui.a_propos_de_nous;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is a_propos_de_nous fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}