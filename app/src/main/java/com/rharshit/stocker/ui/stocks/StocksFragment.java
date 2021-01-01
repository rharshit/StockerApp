package com.rharshit.stocker.ui.stocks;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.rharshit.stocker.R;
import com.rharshit.stocker.base.ui.BaseFragment;
import com.rharshit.stocker.base.widgets.IncludeFloatingActionButton;

import butterknife.BindView;

public class StocksFragment extends BaseFragment<StocksViewModel> implements IncludeFloatingActionButton {

    @BindView(R.id.rv_ticker_list)
    RecyclerView lvTickerList;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_stocks;
    }

    @Override
    public Class<StocksViewModel> getViewModelClass() {
        return StocksViewModel.class;
    }

    @Override
    public void init() {
        lvTickerList.setLayoutManager(new LinearLayoutManager(getContext()));
        lvTickerList.setAdapter(getViewModel().getTickerListAdapter());

        getViewModel().getTickerDataListFirebase().observe(getViewLifecycleOwner(),
                tickerData -> getViewModel().onChangeTickerList(tickerData));

        getViewModel().getTickerDataListMarketstack().observe(getViewLifecycleOwner(),
                tickerData -> getViewModel().onChangeTickerList(tickerData));

        getViewModel().initTickers();
    }

    @Override
    public BaseFragment<StocksViewModel> getFragment() {
        return this;
    }

    @Override
    public boolean isFabRequired() {
        return true;
    }

    @Override
    public void onFabClickListener(View v) {
        Snackbar.make(getView(), "Add stocks", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public boolean onFabLongClickListener(View v) {
        return false;
    }
}