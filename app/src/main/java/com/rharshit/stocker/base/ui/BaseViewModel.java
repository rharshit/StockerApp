package com.rharshit.stocker.base.ui;

import android.content.Context;

import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel extends ViewModel {

    private BaseAppCompatActivity activity;

    public BaseViewModel() {
    }

    public BaseAppCompatActivity getActivity() {
        return activity;
    }

    public Context getContext() {
        return getActivity().getContext();
    }

    public void setActivity(BaseAppCompatActivity activity) {
        this.activity = activity;
    }

    public abstract void initService();

    public abstract void init();
}
