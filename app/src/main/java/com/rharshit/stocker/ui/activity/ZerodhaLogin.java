package com.rharshit.stocker.ui.activity;

import android.os.Bundle;

import com.rharshit.stocker.R;
import com.rharshit.stocker.base.ui.BaseAppCompatLoggedinActivity;

public class ZerodhaLogin extends BaseAppCompatLoggedinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_zerodha_login;
    }

    @Override
    public void init() {
    }
}