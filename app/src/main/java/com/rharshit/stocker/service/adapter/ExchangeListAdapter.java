package com.rharshit.stocker.service.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.rharshit.stocker.R;
import com.rharshit.stocker.StockerApplication;
import com.rharshit.stocker.base.ui.BaseAppCompatLoggedinActivity;
import com.rharshit.stocker.base.widgets.BaseAdapter;
import com.rharshit.stocker.base.widgets.BaseViewHolder;
import com.rharshit.stocker.data.ExchangeData;

import java.util.List;

public class ExchangeListAdapter extends BaseAdapter<ExchangeListAdapter.ExchangeViewHolder, ExchangeData> {

    public ExchangeListAdapter(Context context, List<ExchangeData> data) {
        super(context, data);
    }

    @NonNull
    @Override
    public ExchangeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exchange_list, parent, false);
        return new ExchangeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExchangeViewHolder holder, int position) {
        ExchangeData data = getData(position);
        ((TextView) holder.itemView.findViewById(R.id.tv_exchange_acronym)).setText(data.getAcronym());
        holder.itemView.setOnClickListener(v -> {
            ((StockerApplication) getContext().getApplicationContext()).setExchangeData(data);
            ((BaseAppCompatLoggedinActivity) getContext()).finish();
        });
    }

    public class ExchangeViewHolder extends BaseViewHolder {

        public ExchangeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
