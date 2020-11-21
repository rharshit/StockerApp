package com.rharshit.stocker.base.ui;

import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel extends ViewModel {

    private BaseAppCompatActivity activity;

    public BaseViewModel() {
        initService();
    }

    public BaseAppCompatActivity getActivity() {
        return activity;
    }

    public void setActivity(BaseAppCompatActivity activity) {
        this.activity = activity;
    }

    public abstract void initService();
}
