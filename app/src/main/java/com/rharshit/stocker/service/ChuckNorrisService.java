package com.rharshit.stocker.service;

import com.rharshit.stocker.base.rx.BaseAsyncTask;
import com.rharshit.stocker.base.rx.BaseService;
import com.rharshit.stocker.base.ui.BaseAppCompatActivity;
import com.rharshit.stocker.base.ui.BaseViewModel;
import com.rharshit.stocker.data.Joke;
import com.rharshit.stocker.service.client.ChuckNorrisClient;

import retrofit2.Call;

public class ChuckNorrisService extends BaseService<BaseViewModel, ChuckNorrisClient> {

    public ChuckNorrisService(BaseAppCompatActivity activity, String baseUrl) {
        super(activity, baseUrl);
    }

    @Override
    public Class<ChuckNorrisClient> getServiceClass() {
        return ChuckNorrisClient.class;
    }

    public BaseAsyncTask<Void, Void, Joke> getJokeTask(BaseAsyncTask.IoTask<Joke> onFinish) {
        Call<Joke> jokeCall = getClient().getRandomJoke();
        return createBackgroundIoTask("Getting joke", jokeCall, onFinish);
    }
}
