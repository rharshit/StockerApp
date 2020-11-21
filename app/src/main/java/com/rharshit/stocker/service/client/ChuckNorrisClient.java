package com.rharshit.stocker.service.client;

import com.rharshit.stocker.base.rx.BaseClient;
import com.rharshit.stocker.data.Joke;

import retrofit2.Call;
import retrofit2.http.GET;

import static com.rharshit.stocker.constant.APIConstants.GET_CHUCK_NORRIS_JOKES;

public interface ChuckNorrisClient extends BaseClient {

    @GET(GET_CHUCK_NORRIS_JOKES)
    Call<Joke> getRandomJoke();
}
