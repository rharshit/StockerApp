package com.rharshit.stocker.ui.stocks;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.rharshit.stocker.R;
import com.rharshit.stocker.base.BaseFragment;

public class StocksFragment extends BaseFragment<StocksViewModel> {

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
        getViewModel().getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
    }
}