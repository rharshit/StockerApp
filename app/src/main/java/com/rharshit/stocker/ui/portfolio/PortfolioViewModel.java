package com.rharshit.stocker.ui.portfolio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rharshit.stocker.base.ui.BaseViewModel;

public class PortfolioViewModel extends BaseViewModel {

    private final MutableLiveData<String> mText;

    public PortfolioViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is portfolio fragment");
    }

    @Override
    public void initService() {

    }

    @Override
    public void init() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}