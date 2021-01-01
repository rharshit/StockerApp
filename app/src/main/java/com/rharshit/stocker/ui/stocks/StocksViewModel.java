package com.rharshit.stocker.ui.stocks;


import androidx.lifecycle.MutableLiveData;
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

    @Override
    public void init() {
        tickerListAdapter = new TickerListAdapter(getContext(), new ArrayList<>());
    }

    private List<TickerData> getAllStockList() {
        List<TickerData> list = new ArrayList<>();
        list.addAll(tickerDataListMarketstack.getValue());
        list.addAll(tickerDataListFirebase.getValue());
        return list;
    }

    public void onChangeTickerList() {
        tickerListAdapter.updateList(getAllStockList());
    }

    public void onChangeTickerSearch(CharSequence s) {
        tickerListAdapter.updateList(getAllStockList(s.toString().trim().toLowerCase()));
    }

    private List<TickerData> getAllStockList(String searchText) {
        List<TickerData> filteredList = new ArrayList<>();
        if (searchText.trim().isEmpty()) {
            return getAllStockList();
        } else {
            for (TickerData data : tickerDataListMarketstack.getValue()) {
                if (isTickerMatch(data, searchText)) {
                    filteredList.add(data);
                }
            }
            for (TickerData data : tickerDataListFirebase.getValue()) {
                if (isTickerMatch(data, searchText)) {
                    filteredList.add(data);
                }
            }
        }
        return filteredList;
    }

    private boolean isTickerMatch(TickerData data, String searchText) {
        if (data == null) {
            return false;
        }
        if (data.getName().trim().toLowerCase().contains(searchText)) {
            return true;
        }
        return data.getSymbol().trim().toLowerCase().contains(searchText);
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

    public TickerListAdapter getTickerListAdapter() {
        return tickerListAdapter;
    }

    public void setTickerListAdapter(TickerListAdapter tickerListAdapter) {
        this.tickerListAdapter = tickerListAdapter;
    }
}