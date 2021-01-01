package com.rharshit.stocker;

import android.app.Application;

import com.rharshit.stocker.data.ExchangeData;

public class StockerApplication extends Application {
    private ExchangeData exchangeData;

    public ExchangeData getExchangeData() {
        return exchangeData;
    }

    public void setExchangeData(ExchangeData exchangeData) {
        this.exchangeData = exchangeData;
    }
}
