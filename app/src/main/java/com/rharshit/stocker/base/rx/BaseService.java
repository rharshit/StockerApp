package com.rharshit.stocker.base.rx;

import com.rharshit.stocker.base.ui.BaseAppCompatActivity;
import com.rharshit.stocker.base.ui.BaseViewModel;

public class BaseService<V extends BaseViewModel> {

    private final BaseAppCompatActivity activity;
    private final V viewModel;

    public BaseService(V viewModel) {
        this.viewModel = viewModel;
        this.activity = null;
    }

    public BaseService(BaseAppCompatActivity activity) {
        this.viewModel = null;
        this.activity = activity;
    }

    protected BaseAppCompatActivity getActivity() {
        if (getViewModel() != null) {
            return getViewModel().getActivity();
        } else {
            return this.activity;
        }
    }

    protected V getViewModel() {
        return viewModel;
    }
}
