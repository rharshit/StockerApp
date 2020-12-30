package com.rharshit.stocker.service.client;

import com.rharshit.stocker.base.rx.BaseClient;
import com.rharshit.stocker.data.BaseMarketstackData;
import com.rharshit.stocker.data.ExchangeData;
import com.rharshit.stocker.data.TickerData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.rharshit.stocker.constant.APIConstants.ACCESS_KEY;
import static com.rharshit.stocker.constant.APIConstants.EXCHANGE;
import static com.rharshit.stocker.constant.APIConstants.GET_MARKETSTACK_EXCHANGES;
import static com.rharshit.stocker.constant.APIConstants.GET_MARKETSTACK_TICKERS;
import static com.rharshit.stocker.constant.APIConstants.LIMIT;
import static com.rharshit.stocker.constant.APIConstants.OFFSET;

public interface MarketStackClient extends BaseClient {
    @GET(GET_MARKETSTACK_EXCHANGES)
    Call<BaseMarketstackData<ExchangeData>> getExchanges(@Query(ACCESS_KEY) String accessKey,
                                                         @Query(LIMIT) int limit);

    @GET(GET_MARKETSTACK_TICKERS)
    Call<BaseMarketstackData<TickerData>> getTickers(@Query(ACCESS_KEY) String accessKey,
                                                     @Query(LIMIT) int limit,
                                                     @Query(EXCHANGE) String exchange);

    @GET(GET_MARKETSTACK_TICKERS)
    Call<BaseMarketstackData<TickerData>> getTickers(@Query(ACCESS_KEY) String accessKey,
                                                     @Query(LIMIT) int limit,
                                                     @Query(OFFSET) int offset,
                                                     @Query(EXCHANGE) String exchange);
}
