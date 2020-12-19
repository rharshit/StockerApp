package com.rharshit.stocker.service;

import android.annotation.SuppressLint;
import android.util.Log;

import com.rharshit.stocker.base.rx.BaseAsyncTask;
import com.rharshit.stocker.base.rx.BaseService;
import com.rharshit.stocker.base.ui.BaseAppCompatActivity;
import com.rharshit.stocker.base.ui.BaseViewModel;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.User;

import org.json.JSONException;

import java.io.IOException;

import static android.content.ContentValues.TAG;
import static com.rharshit.stocker.constant.APIConstants.ZERODHA_API_SECRET;

public class ZerodhaService extends BaseService<BaseViewModel> {
    private static KiteConnect kiteSdk;

    public ZerodhaService(BaseAppCompatActivity activity, KiteConnect sdk) {
        super(activity);
        kiteSdk = sdk;
    }

    @SuppressLint("StaticFieldLeak")
    public BaseAsyncTask<String, Void, User> getUserTask(BaseAsyncTask.Task<User> onFinish) {
        return new BaseAsyncTask<String, Void, User>(getActivity(), true, "Logging in to Zerodha") {
            @Override
            protected User doInBackground(String... strings) {
                try {
                    return kiteSdk.generateSession(strings[0], ZERODHA_API_SECRET);
                } catch (KiteException | JSONException | IOException e) {
                    Log.e(TAG, "initZerodha: ", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);
                onFinish.execute(user);
            }
        };
    }

    @SuppressLint("StaticFieldLeak")
    public void invalidateSession() {
        new BaseAsyncTask<Void, Void, Void>(getActivity(), false, "Removing Zerodha access token") {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    kiteSdk.invalidateAccessToken();
                } catch (IOException | KiteException | JSONException e) {
                    Log.e(TAG, "doInBackground: ", e);
                }
                return null;
            }
        }.execute();
    }
}
