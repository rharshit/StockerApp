package com.rharshit.stocker.base.ui;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

public class BaseViewModel extends ViewModel {

    MutableLiveData<Map<Integer, AsyncTask>> blockedUiTasks;
    MutableLiveData<Map<Integer, AsyncTask>> nonBlockedUiTasks;
    private BaseAppCompatActivity activity;

    public BaseViewModel() {
        blockedUiTasks = new MutableLiveData<>(new HashMap<>());
        nonBlockedUiTasks = new MutableLiveData<>(new HashMap<>());
    }

    public void setActivity(BaseAppCompatActivity activity) {
        this.activity = activity;
    }
}
