package com.rharshit.stocker.service.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.blongho.country_data.World;
import com.rharshit.stocker.R;
import com.rharshit.stocker.StockerApplication;
import com.rharshit.stocker.base.ui.BaseAppCompatLoggedinActivity;
import com.rharshit.stocker.base.widgets.BaseAdapter;
import com.rharshit.stocker.base.widgets.BaseViewHolder;
import com.rharshit.stocker.data.ExchangeData;
import com.rharshit.stocker.databinding.ItemExchangeListBinding;

import java.util.List;

import static android.content.ContentValues.TAG;

public class ExchangeListAdapter extends BaseAdapter<ExchangeListAdapter.ExchangeViewHolder, ExchangeData> {

    public ExchangeListAdapter(Context context, List<ExchangeData> data) {
        super(context, data);
    }

    @NonNull
    @Override
    public ExchangeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemExchangeListBinding view = DataBindingUtil
                .inflate(inflater, R.layout.item_exchange_list, parent, false);
        World.init(parent.getContext().getApplicationContext());
        return new ExchangeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExchangeViewHolder holder, int position) {
        ExchangeData data = getData(position);

        try {
            ((ItemExchangeListBinding) holder.getViewDataBinding()).setExchangeData(data);
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder: ", e);
        }


        holder.getItemView().setOnClickListener(v -> {
            ((StockerApplication) getContext().getApplicationContext()).setExchangeData(data);
            ((StockerApplication) getContext().getApplicationContext()).removeTickerDataList();
            ((BaseAppCompatLoggedinActivity) getContext()).finish();
        });
    }

    public class ExchangeViewHolder extends BaseViewHolder {

        public ExchangeViewHolder(@NonNull ItemExchangeListBinding viewDataBinding) {
            super(viewDataBinding);
        }
    }
}
