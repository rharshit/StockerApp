package com.rharshit.stocker.service;

import com.rharshit.stocker.base.rx.BaseAsyncTask;
import com.rharshit.stocker.base.rx.BaseService;
import com.rharshit.stocker.base.ui.BaseAppCompatActivity;
import com.rharshit.stocker.base.ui.BaseViewModel;
import com.rharshit.stocker.data.BaseMarketstackData;
import com.rharshit.stocker.data.ExchangeData;
import com.rharshit.stocker.service.client.MarketStackClient;

import retrofit2.Call;

import static com.rharshit.stocker.constant.APIConstants.MARKETSTACK_API_KEY;

public class MarketStackService extends BaseService<BaseViewModel, MarketStackClient> {

    public MarketStackService(BaseAppCompatActivity activity, String baseUrl) {
        super(activity, baseUrl);
    }

    @Override
    public Class<MarketStackClient> getServiceClass() {
        return MarketStackClient.class;
    }

    public BaseAsyncTask<Void, Void, BaseMarketstackData<ExchangeData>> getExcahnges(BaseAsyncTask.IoTask<BaseMarketstackData<ExchangeData>> onFinish) {
        Call<BaseMarketstackData<ExchangeData>> exchangeCall = getCachedClient().getExchanges(MARKETSTACK_API_KEY, 1000);
        return createForegroundIoTask("Fetching excahnges", exchangeCall, onFinish);
    }
}
