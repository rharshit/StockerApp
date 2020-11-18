package com.rharshit.stocker.ui.stocks;

import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.rharshit.stocker.R;
import com.rharshit.stocker.base.ui.BaseFragment;
import com.rharshit.stocker.base.widgets.IncludeFloatingActionButton;

public class StocksFragment extends BaseFragment<StocksViewModel> implements IncludeFloatingActionButton {

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
        final TextView textView = getView().findViewById(R.id.text_stocks);
        getViewModel().getText().observe(getViewLifecycleOwner(), textView::setText);
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