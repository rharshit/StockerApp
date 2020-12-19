package com.rharshit.stocker.base.rx;

import android.annotation.SuppressLint;
import android.util.Log;

import com.rharshit.stocker.base.data.BaseData;
import com.rharshit.stocker.base.ui.BaseAppCompatActivity;
import com.rharshit.stocker.base.ui.BaseViewModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public abstract class BaseRestService<V extends BaseViewModel, C extends BaseClient>
        extends BaseService<V> {

    private final String baseUrl;
    private C client;

    public BaseRestService(V viewModel, String baseUrl) {
        super(viewModel);

        this.baseUrl = baseUrl;
        initRetrofit();
    }

    public BaseRestService(BaseAppCompatActivity activity, String baseUrl) {
        super(activity);

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
                           BaseAsyncTask.Task<Z> onFinish) {
        return createIoTask(name, call, onFinish, false);
    }

    public <Z extends BaseData> BaseAsyncTask<Void, Void, Z>
    createForegroundIoTask(String name,
                           Call<Z> call,
                           BaseAsyncTask.Task<Z> onFinish) {
        return createIoTask(name, call, onFinish, true);
    }

    @SuppressLint("StaticFieldLeak")
    private <Z extends BaseData> BaseAsyncTask<Void, Void, Z>
    createIoTask(String name, Call<Z> call,
                 BaseAsyncTask.Task<Z> onFinish,
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

    protected C getClient() {
        return client;
    }

    public abstract Class<C> getServiceClass();
}
