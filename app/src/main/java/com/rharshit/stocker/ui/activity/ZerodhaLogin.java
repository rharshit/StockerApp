package com.rharshit.stocker.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.rharshit.stocker.R;
import com.rharshit.stocker.base.ui.BaseAppCompatActivity;

import static com.rharshit.stocker.constant.APIConstants.ZERODHA_LOGIN_REQUEST_TOKEN;
import static com.rharshit.stocker.constant.APIConstants.ZERODHA_LOGIN_STATUS;
import static com.rharshit.stocker.constant.DBConstants.ZERODHA_SHARED_PREF;
import static com.rharshit.stocker.constant.IntentConstants.ZERODHA_LOGIN_URI;
import static com.rharshit.stocker.constant.IntentConstants.ZERODHA_REQUEST_TOKEN;
import static com.rharshit.stocker.constant.SharedPreferenceConstants.KEY_ZERODHA_REQUEST_TOKEN;

public class ZerodhaLogin extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_zerodha_login;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    @Override
    public void init() {
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        String loginUrl = intent.getStringExtra(ZERODHA_LOGIN_URI);
        if (loginUrl != null) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(loginUrl)));
        } else {
            boolean success = false;
            String action = intent.getAction();
            Uri data = intent.getData();
            String requestToken = null;
            if (Intent.ACTION_VIEW.equals(action) && data != null) {
                requestToken = data.getQueryParameter(ZERODHA_LOGIN_REQUEST_TOKEN);
                success = "success".equals(data.getQueryParameter(ZERODHA_LOGIN_STATUS));
            }

            if (success) {
                SharedPreferences.Editor editor = getContext()
                        .getSharedPreferences(ZERODHA_SHARED_PREF, Context.MODE_PRIVATE)
                        .edit();
                editor.putString(KEY_ZERODHA_REQUEST_TOKEN, requestToken);
                editor.apply();

                Intent returnIntent = getIntent();
                returnIntent.putExtra(ZERODHA_REQUEST_TOKEN, requestToken);
                setIntent(returnIntent);
                finish();
            }
        }
    }
}