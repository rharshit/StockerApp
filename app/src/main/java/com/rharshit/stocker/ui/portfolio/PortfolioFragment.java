package com.rharshit.stocker.ui.portfolio;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.rharshit.stocker.R;
import com.rharshit.stocker.base.BaseFragment;

public class PortfolioFragment extends BaseFragment<PortfolioViewModel> {

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
}