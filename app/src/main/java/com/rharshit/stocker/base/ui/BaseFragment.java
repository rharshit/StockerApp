package com.rharshit.stocker.base.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public abstract class BaseFragment<V extends BaseViewModel> extends Fragment {

    V viewModel;
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(this).get(getViewModelClass());
        view = inflater.inflate(getLayoutResource(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public V getViewModel() {
        return viewModel;
    }

    @Override
    public View getView() {
        return view;
    }

    public abstract @LayoutRes
    int getLayoutResource();

    public abstract Class<V> getViewModelClass();

    public abstract void init();
}
