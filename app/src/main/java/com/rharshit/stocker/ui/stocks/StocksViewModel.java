package com.rharshit.stocker.ui.stocks;


import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rharshit.stocker.R;
import com.rharshit.stocker.StockerApplication;
import com.rharshit.stocker.base.ui.BaseViewModel;
import com.rharshit.stocker.data.TickerData;
import com.rharshit.stocker.service.adapter.TickerListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class StocksViewModel extends BaseViewModel {

    @BindView(R.id.et_ticker_search)
    TextView tvTickerSearch;

    @BindView(R.id.rv_ticker_list)
    RecyclerView lvTickerList;

    private MutableLiveData<List<TickerData>> tickerDataListMarketstack;
    private MutableLiveData<List<TickerData>> tickerDataListFirebase;

    private TickerListAdapter tickerListAdapter;

    public StocksViewModel() {
        tickerDataListMarketstack = new MutableLiveData<>();
        tickerDataListFirebase = new MutableLiveData<>();
    }

    public void initTickers() {
        tickerListAdapter = new TickerListAdapter(getContext(), new ArrayList<>());
        if (lvTickerList == null) {
            //TODO: Fix BinderView
            lvTickerList = getActivity().findViewById(R.id.rv_ticker_list);
        }
        lvTickerList.setLayoutManager(new LinearLayoutManager(getContext()));
        lvTickerList.setAdapter(tickerListAdapter);

        getAllTickerData();

    }

    private void getAllTickerData() {
        tickerDataListMarketstack.setValue(getMarketstackTickerList());
        tickerDataListFirebase.setValue(getFirebaseTickerList());
    }

    //TODO: Add firebase stock data
    private List<TickerData> getFirebaseTickerList() {
        return new ArrayList<>();
    }

    private List<TickerData> getMarketstackTickerList() {
        return ((StockerApplication) getActivity().getApplication()).getTickerDataList();
    }

    @Override
    public void initService() {

    }

    private List<TickerData> getAllStockList() {
        List<TickerData> list = new ArrayList<>();
        list.addAll(tickerDataListMarketstack.getValue());
        list.addAll(tickerDataListFirebase.getValue());
        return list;
    }

    public void onChangeTickerList(List<TickerData> tickerData) {
        tickerListAdapter.updateList(getAllStockList());
    }

    public MutableLiveData<List<TickerData>> getTickerDataListMarketstack() {
        return tickerDataListMarketstack;
    }

    public void setTickerDataListMarketstack(MutableLiveData<List<TickerData>> tickerDataListMarketstack) {
        this.tickerDataListMarketstack = tickerDataListMarketstack;
    }

    public MutableLiveData<List<TickerData>> getTickerDataListFirebase() {
        return tickerDataListFirebase;
    }

    public void setTickerDataListFirebase(MutableLiveData<List<TickerData>> tickerDataListFirebase) {
        this.tickerDataListFirebase = tickerDataListFirebase;
    }
}