package com.example.myapplication.ui.sahti_fi_ydi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is sahti_fi_ydi fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}