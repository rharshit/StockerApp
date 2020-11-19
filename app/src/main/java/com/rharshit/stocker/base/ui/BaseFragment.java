package com.rharshit.stocker.base.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.rharshit.stocker.R;
import com.rharshit.stocker.base.widgets.BaseExtendedFloatingActionButton;
import com.rharshit.stocker.base.widgets.BaseFloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

public abstract class BaseFragment<V extends BaseViewModel> extends Fragment {

    @BindView(R.id.extended_fab)
    BaseExtendedFloatingActionButton extendedFab;
    @BindView(R.id.fab)
    BaseFloatingActionButton fab;
    private V viewModel;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(this).get(getViewModelClass());
        viewModel.setActivity((BaseAppCompatActivity) this.getActivity());
        view = inflater.inflate(getLayoutResource(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        bind(getView());
        addObservables();
        addListeners();
    }

    protected void addListeners() {
        Log.d(TAG, "addListeners: " + extendedFab);
        Log.d(TAG, "addListeners: " + fab);
        if (extendedFab != null && isExtendedFabRequired()) {
            extendedFab.setOnClickListener(getFragment()::onExtendedFabClickListener);
            extendedFab.setOnLongClickListener(getFragment()::onExtendedFabLongClickListener);
        }
        if (fab != null && isFabRequired()) {
            fab.setOnClickListener(getFragment()::onFabClickListener);
            fab.setOnLongClickListener(getFragment()::onFabLongClickListener);
        }
    }

    public void onExtendedFabClickListener(View v) {

    }

    public boolean onExtendedFabLongClickListener(View v) {
        return false;
    }

    public void onFabClickListener(View v) {

    }

    public boolean onFabLongClickListener(View v) {
        return false;
    }

    private void bind(View view) {
        try {
            ButterKnife.bind(this, view);
        } catch (IllegalStateException e) {
            bind((View) view.getParent());
        } catch (Exception e) {
            Log.e(TAG, "bind: ", e);
        }
    }

    private void addObservables() {
        if (extendedFab != null) {
            extendedFab.setVisibility(isExtendedFabRequired() ? View.VISIBLE : View.GONE);
        }
        if (fab != null) {
            fab.setVisibility(isFabRequired() ? View.VISIBLE : View.GONE);
        }
    }

    protected boolean isFabRequired() {
        return false;
    }

    protected boolean isExtendedFabRequired() {
        return false;
    }

    public V getViewModel() {
        return viewModel;
    }

    @Override
    public View getView() {
        return view;
    }

    public void addLoading(String s) {
        getBaseActivity().addLoading(s);
    }

    public void removeLoading(String s) {
        getBaseActivity().removeLoading(s);
    }

    private BaseAppCompatActivity getBaseActivity() {
        return getViewModel().getActivity();
    }

    public abstract @LayoutRes
    int getLayoutResource();

    public abstract Class<V> getViewModelClass();

    public abstract void init();

    public abstract BaseFragment<V> getFragment();
}
