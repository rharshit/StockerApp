package com.rharshit.stocker.ui.stocks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rharshit.stocker.base.BaseViewModel;

public class StocksViewModel extends BaseViewModel {

    private final MutableLiveData<String> mText;

    public StocksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is stocks fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}