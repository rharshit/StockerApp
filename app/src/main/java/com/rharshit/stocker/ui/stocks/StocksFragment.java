package com.rharshit.stocker.ui.stocks;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
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

    @BindView(R.id.et_ticker_search)
    TextView tvTickerSearch;

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

        tvTickerSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getViewModel().onChangeTickerSearch(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getViewModel().getTickerDataListFirebase().observe(getViewLifecycleOwner(),
                tickerData -> getViewModel().onChangeTickerList());

        getViewModel().getTickerDataListMarketstack().observe(getViewLifecycleOwner(),
                tickerData -> getViewModel().onChangeTickerList());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
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