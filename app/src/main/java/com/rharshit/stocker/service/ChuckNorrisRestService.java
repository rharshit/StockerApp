package com.rharshit.stocker.service;

import com.rharshit.stocker.base.rx.BaseAsyncTask;
import com.rharshit.stocker.base.rx.BaseRestService;
import com.rharshit.stocker.base.ui.BaseAppCompatActivity;
import com.rharshit.stocker.base.ui.BaseViewModel;
import com.rharshit.stocker.data.Joke;
import com.rharshit.stocker.service.client.ChuckNorrisClient;

import retrofit2.Call;

public class ChuckNorrisRestService extends BaseRestService<BaseViewModel, ChuckNorrisClient> {

    public ChuckNorrisRestService(BaseAppCompatActivity activity, String baseUrl) {
        super(activity, baseUrl);
    }

    @Override
    public Class<ChuckNorrisClient> getServiceClass() {
        return ChuckNorrisClient.class;
    }

    public BaseAsyncTask<Void, Void, Joke> getJokeTask(BaseAsyncTask.Task<Joke> onFinish) {
        Call<Joke> jokeCall = getClient().getRandomJoke();
        return createBackgroundIoTask("Getting joke", jokeCall, onFinish);
    }
}
