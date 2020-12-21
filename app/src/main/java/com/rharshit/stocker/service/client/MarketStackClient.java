package com.rharshit.stocker.service.client;

import com.rharshit.stocker.base.rx.BaseClient;
import com.rharshit.stocker.data.BaseMarketstackData;
import com.rharshit.stocker.data.ExchangeData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.rharshit.stocker.constant.APIConstants.ACCESS_KEY;
import static com.rharshit.stocker.constant.APIConstants.GET_MARKETSTACK_EXCHANGES;
import static com.rharshit.stocker.constant.APIConstants.LIMIT;

public interface MarketStackClient extends BaseClient {
    @GET(GET_MARKETSTACK_EXCHANGES)
    Call<BaseMarketstackData<ExchangeData>> getExchanges(@Query(ACCESS_KEY) String accessKey,
                                                         @Query(LIMIT) int limit);
}
