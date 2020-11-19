package com.rharshit.stocker.base.ui;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseAppCompatActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
    }

    public Context getContext() {
        return context;
    }

    public void makeToast(String message, int duration) {
        Toast.makeText(getContext(), message, duration).show();
    }
}
