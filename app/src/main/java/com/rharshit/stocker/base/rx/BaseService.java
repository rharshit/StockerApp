package com.rharshit.stocker.base.rx;

import android.util.Log;

import com.rharshit.stocker.base.data.BaseData;
import com.rharshit.stocker.base.ui.BaseAppCompatActivity;
import com.rharshit.stocker.base.ui.BaseViewModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public abstract class BaseService<V extends BaseViewModel, C extends BaseClient> {

    private final BaseAppCompatActivity activity;
    private final V viewModel;
    private final String baseUrl;

    private C client;

    public BaseService(V viewModel, String baseUrl) {
        this.viewModel = viewModel;
        this.activity = null;
        this.baseUrl = baseUrl;
        initRetrofit();
    }

    public BaseService(BaseAppCompatActivity activity, String baseUrl) {
        this.viewModel = null;
        this.activity = activity;
        this.baseUrl = baseUrl;
        initRetrofit();
    }

    private void initRetrofit() {
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(this.baseUrl)
                        .addConverterFactory(GsonConverterFactory.create()).build();

        this.client = retrofit.create(getServiceClass());
    }

    public <Z extends BaseData> BaseAsyncTask<Void, Void, Z>
    createBackgroundIoTask(String name,
                           Call<Z> call,
                           BaseAsyncTask.IoTask<Z> onFinish) {
        return createIoTask(name, call, onFinish, false);
    }

    public <Z extends BaseData> BaseAsyncTask<Void, Void, Z>
    createForegroundIoTask(String name,
                           Call<Z> call,
                           BaseAsyncTask.IoTask<Z> onFinish) {
        return createIoTask(name, call, onFinish, true);
    }

    private <Z extends BaseData> BaseAsyncTask<Void, Void, Z>
    createIoTask(String name, Call<Z> call,
                 BaseAsyncTask.IoTask<Z> onFinish,
                 boolean isUiBlocked) {

        return new BaseAsyncTask<Void, Void, Z>(getActivity(), isUiBlocked, name) {
            @Override
            protected Z doInBackground(Void... voids) {
                try {
                    return call.execute().body();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: ", e);
                    return (Z) new BaseData(true);
                }
            }

            @Override
            protected void onPostExecute(Z z) {
                super.onPostExecute(z);
                try {
                    onFinish.execute(z);
                } catch (Exception e) {
                    Log.e(TAG, "onPostExecute: ", e);
                }
            }
        };
    }

    private BaseAppCompatActivity getActivity() {
        if (getViewModel() != null) {
            return getViewModel().getActivity();
        } else {
            return this.activity;
        }
    }

    public V getViewModel() {
        return viewModel;
    }

    public C getClient() {
        return client;
    }

    public abstract Class<C> getServiceClass();
}
