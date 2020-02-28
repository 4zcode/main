package com.example.myapplication.ui.don_de_sang;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is don_de_sang fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}