package com.rharshit.stocker.base.rx;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.rharshit.stocker.base.data.BaseData;
import com.rharshit.stocker.base.ui.BaseAppCompatActivity;
import com.rharshit.stocker.base.ui.BaseViewModel;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public abstract class BaseService<V extends BaseViewModel, C extends BaseClient> {

    private final BaseAppCompatActivity activity;
    private final V viewModel;
    private final String baseUrl;

    private C client;
    private static Interceptor onlineInterceptor;
    private static Interceptor offlineInterceptor;
    private static Cache cache;
    private C cachedClient;

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

        OkHttpClient cachedOkHttpClient = generateCachedOkhttpClient();

        Retrofit cachedRetrofit =
                new Retrofit.Builder()
                        .baseUrl(this.baseUrl)
                        .client(cachedOkHttpClient)
                        .addConverterFactory(GsonConverterFactory.create()).build();

        this.cachedClient = cachedRetrofit.create(getServiceClass());
    }

    private OkHttpClient generateCachedOkhttpClient() {
        long cacheSize = (5 * 1024 * 1024);
        if (onlineInterceptor == null) {
            onlineInterceptor = chain -> {
                okhttp3.Response response = chain.proceed(chain.request());
                int maxAge = 60 * 60 * 24;
                Log.d(TAG, "HTTP Interceptor: Adding to cache:\n" + response.request().url());
                return response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Pragma")
                        .build();
            };
        }

        if (offlineInterceptor == null) {
            offlineInterceptor = chain -> {
                Request request = chain.request();
                if (!isNetworkAvailable(getContext())) {
                    int maxStale = 60 * 60 * 24 * 7;
                    request = request.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                Log.d(TAG, "HTTP Interceptor: Loading from cache:\n" + request.url());
                return chain.proceed(request);
            };
        }

        if (cache == null) {
            cache = new Cache(getActivity().getCacheDir(), cacheSize);
        }

        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(offlineInterceptor)
                .addNetworkInterceptor(onlineInterceptor)
                .build();
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
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
                    Response<Z> response = call.execute();
                    return response.body();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: ", e);
                    return (Z) new BaseData(false);
                }
            }

            @Override
            protected void onPostExecute(Z z) {
                super.onPostExecute(z);
                try {
                    if (z instanceof BaseData) {
                        if (z.getSuccess() == null) {
                            z.setSuccess(true);
                        }
                    }
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

    public Context getContext() {
        return getActivity();
    }


    public C getClient() {
        return client;
    }

    public C getCachedClient() {
        return cachedClient;
    }

    public abstract Class<C> getServiceClass();
}
