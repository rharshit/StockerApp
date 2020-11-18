package com.rharshit.stocker.ui.dashboard;

import android.widget.TextView;

import com.rharshit.stocker.R;
import com.rharshit.stocker.base.ui.BaseFragment;

public class DashboardFragment extends BaseFragment<DashboardViewModel> {

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public Class<DashboardViewModel> getViewModelClass() {
        return DashboardViewModel.class;
    }

    @Override
    public void init() {
        final TextView textView = getView().findViewById(R.id.text_dashboard);
        getViewModel().getText().observe(getViewLifecycleOwner(), textView::setText);
    }

    @Override
    public BaseFragment<DashboardViewModel> getFragment() {
        return this;
    }
}