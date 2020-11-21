package com.rharshit.stocker.ui.portfolio;

import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.rharshit.stocker.R;
import com.rharshit.stocker.base.ui.BaseFragment;
import com.rharshit.stocker.base.widgets.IncludeExtendedFloatingActionButton;

public class PortfolioFragment extends BaseFragment<PortfolioViewModel> implements IncludeExtendedFloatingActionButton {

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_portfolio;
    }

    @Override
    public Class<PortfolioViewModel> getViewModelClass() {
        return PortfolioViewModel.class;
    }

    @Override
    public void init() {
        final TextView textView = getView().findViewById(R.id.text_portfolio);
        getViewModel().getText().observe(getViewLifecycleOwner(), textView::setText);
    }

    @Override
    public BaseFragment<PortfolioViewModel> getFragment() {
        return this;
    }

    @Override
    public boolean isExtendedFabRequired() {
        return true;
    }

    @Override
    public void onExtendedFabClickListener(View v) {
        Snackbar.make(getView(), "Getting a joke for you", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        getViewModel().showJoke();
    }

    @Override
    public boolean onExtendedFabLongClickListener(View v) {
        return false;
    }
}