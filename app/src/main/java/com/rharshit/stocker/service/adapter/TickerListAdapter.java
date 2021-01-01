package com.rharshit.stocker.service.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.rharshit.stocker.R;
import com.rharshit.stocker.base.widgets.BaseAdapter;
import com.rharshit.stocker.base.widgets.BaseViewHolder;
import com.rharshit.stocker.data.TickerData;
import com.rharshit.stocker.databinding.ItemTickerListBinding;

import java.util.List;

import static android.content.ContentValues.TAG;

public class TickerListAdapter extends BaseAdapter<BaseViewHolder, TickerData> {

    public TickerListAdapter(Context context, List<TickerData> data) {
        super(context, data);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTickerListBinding view = DataBindingUtil
                .inflate(inflater, R.layout.item_ticker_list, parent, false);
        return new TickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        TickerData data = getData(position);

        if (data.getLtp() == 0) {
            refreshStockData(data);
        }
        try {
            ((ItemTickerListBinding) holder.getViewDataBinding()).setTickerData(data);
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder: ", e);
        }


        holder.getItemView().setOnClickListener(v -> {
            refreshStockData(data);
        });
    }

    //TODO: Add logic to refresh ltp
    private void refreshStockData(TickerData data) {
    }

    public class TickerViewHolder extends BaseViewHolder {

        public TickerViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding);
        }
    }
}
