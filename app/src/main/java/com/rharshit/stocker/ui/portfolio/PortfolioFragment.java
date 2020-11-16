package com.rharshit.stocker.ui.portfolio;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

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
        getViewModel().getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
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
        Snackbar.make(getView(), "Add portfolio", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public boolean onExtendedFabLongClickListener(View v) {
        return false;
    }
}