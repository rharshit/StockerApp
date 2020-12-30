package com.rharshit.stocker;

import android.app.Application;

import com.rharshit.stocker.data.ExchangeData;
import com.rharshit.stocker.data.TickerData;

import java.util.List;

public class StockerApplication extends Application {
    private ExchangeData exchangeData;
    private List<TickerData> tickerDataList;

    public ExchangeData getExchangeData() {
        return exchangeData;
    }

    public void setExchangeData(ExchangeData exchangeData) {
        this.exchangeData = exchangeData;
    }

    public List<TickerData> getTickerDataList() {
        return tickerDataList;
    }

    public void setTickerDataList(List<TickerData> tickerDataList) {
        this.tickerDataList = tickerDataList;
    }
}
