package com.example.conversapro.ui.livestreamSubsystem;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    // Display text view
    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Chat Log");
    }

    public LiveData<String> getText() {
        return mText;
    }
}