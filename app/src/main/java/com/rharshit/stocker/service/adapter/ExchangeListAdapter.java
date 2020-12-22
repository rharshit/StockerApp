package com.rharshit.stocker.service.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blongho.country_data.World;
import com.rharshit.stocker.R;
import com.rharshit.stocker.StockerApplication;
import com.rharshit.stocker.base.ui.BaseAppCompatLoggedinActivity;
import com.rharshit.stocker.base.widgets.BaseAdapter;
import com.rharshit.stocker.base.widgets.BaseViewHolder;
import com.rharshit.stocker.data.ExchangeData;

import java.util.List;

import static android.content.ContentValues.TAG;

public class ExchangeListAdapter extends BaseAdapter<ExchangeListAdapter.ExchangeViewHolder, ExchangeData> {

    public ExchangeListAdapter(Context context, List<ExchangeData> data) {
        super(context, data);
    }

    @NonNull
    @Override
    public ExchangeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exchange_list, parent, false);
        World.init(parent.getContext().getApplicationContext());
        return new ExchangeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExchangeViewHolder holder, int position) {
        ExchangeData data = getData(position);

        try {
            ((TextView) holder.itemView.findViewById(R.id.tv_exchange_acronym)).setText(getDisplayAcronym(data));
            ((TextView) holder.itemView.findViewById(R.id.tv_exchange_name)).setText(data.getName());
            ((TextView) holder.itemView.findViewById(R.id.tv_exchange_website)).setText(data.getWebsite());
            ((TextView) holder.itemView.findViewById(R.id.tv_exchange_currency)).setText(data.getCurrency().toString());
            ((TextView) holder.itemView.findViewById(R.id.tv_exchange_timezone)).setText(data.getTimezone().getTimezone());
            ((ImageView) holder.itemView.findViewById(R.id.iv_exchange_country_flag)).setImageResource(getWorldFlagId(data));
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder: ", e);
        }


        holder.itemView.setOnClickListener(v -> {
            ((StockerApplication) getContext().getApplicationContext()).setExchangeData(data);
            ((BaseAppCompatLoggedinActivity) getContext()).finish();
        });
    }

    private int getWorldFlagId(ExchangeData data) {
        int id;
        id = World.getFlagOf(data.getCountry());
        if (id == 2131165401) {
            id = World.getFlagOf(data.getCountryCode());
        }
        return id;
    }

    private String getDisplayAcronym(ExchangeData data) {
        if (data.getMic() == null || data.getMic().trim().isEmpty()) {
            return data.getAcronym();
        }
        if (data.getAcronym() == null || data.getAcronym().trim().isEmpty()) {
            return data.getMic();
        }
        return data.getMic() + "/" + data.getAcronym();
    }

    public class ExchangeViewHolder extends BaseViewHolder {

        public ExchangeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
