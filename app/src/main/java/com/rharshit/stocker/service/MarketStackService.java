package com.rharshit.stocker.service;

import com.rharshit.stocker.base.rx.BaseAsyncTask;
import com.rharshit.stocker.base.rx.BaseService;
import com.rharshit.stocker.base.ui.BaseAppCompatActivity;
import com.rharshit.stocker.base.ui.BaseViewModel;
import com.rharshit.stocker.data.BaseMarketstackData;
import com.rharshit.stocker.data.ExchangeData;
import com.rharshit.stocker.data.TickerData;
import com.rharshit.stocker.service.client.MarketStackClient;

import retrofit2.Call;

import static com.rharshit.stocker.constant.APIConstants.MARKETSTACK_API_KEY;
import static com.rharshit.stocker.constant.Constants.MAX_MARKETSTACK_LIMIT;

public class MarketStackService extends BaseService<BaseViewModel, MarketStackClient> {

    public MarketStackService(BaseAppCompatActivity activity, String baseUrl) {
        super(activity, baseUrl);
    }

    @Override
    public Class<MarketStackClient> getServiceClass() {
        return MarketStackClient.class;
    }

    public BaseAsyncTask<Void, Void, BaseMarketstackData<ExchangeData>> getExchanges(BaseAsyncTask.IoTask<BaseMarketstackData<ExchangeData>> onFinish) {
        Call<BaseMarketstackData<ExchangeData>> exchangeCall = getCachedClient().getExchanges(MARKETSTACK_API_KEY, MAX_MARKETSTACK_LIMIT);
        return createForegroundIoTask("Fetching exchanges", exchangeCall, onFinish);
    }

    public BaseAsyncTask<Void, Void, BaseMarketstackData<TickerData>> getTickers(String exchange, BaseAsyncTask.IoTask<BaseMarketstackData<TickerData>> onFinish) {
        Call<BaseMarketstackData<TickerData>> tickerCall = getCachedClient().getTickers(MARKETSTACK_API_KEY, MAX_MARKETSTACK_LIMIT, exchange);
        return createBackgroundIoTask("Fetching tickers", tickerCall, onFinish);
    }

    public BaseAsyncTask<Void, Void, BaseMarketstackData<TickerData>> getTickers(String exchange, int offset, BaseAsyncTask.IoTask<BaseMarketstackData<TickerData>> onFinish) {
        Call<BaseMarketstackData<TickerData>> tickerCall = getCachedClient().getTickers(MARKETSTACK_API_KEY, MAX_MARKETSTACK_LIMIT, offset, exchange);
        return createBackgroundIoTask("Fetching tickers", tickerCall, onFinish);
    }
}
