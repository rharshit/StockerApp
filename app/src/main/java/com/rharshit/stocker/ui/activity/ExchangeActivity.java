package com.rharshit.stocker.ui.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rharshit.stocker.R;
import com.rharshit.stocker.base.rx.BaseAsyncTask;
import com.rharshit.stocker.base.ui.BaseAppCompatLoggedinActivity;
import com.rharshit.stocker.data.BaseMarketstackData;
import com.rharshit.stocker.data.ExchangeData;
import com.rharshit.stocker.service.MarketStackService;
import com.rharshit.stocker.service.adapter.ExchangeListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rharshit.stocker.constant.APIConstants.BASE_URL_MARKETSTACK;

public class ExchangeActivity extends BaseAppCompatLoggedinActivity {

    @BindView(R.id.et_exchange_search)
    TextView tvExchangeSearch;

    @BindView(R.id.lv_exchange_list)
    RecyclerView lvExchangeList;

    private List<ExchangeData> exchangeDataList;

    private MarketStackService marketStackService;

    private ExchangeListAdapter exchangeListAdapter;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_exchange;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);

        initExchanges();

        tvExchangeSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString();
                List<ExchangeData> filteredList;
                if (searchText.trim().isEmpty()) {
                    filteredList = exchangeDataList;
                } else {
                    filteredList = new ArrayList<>();
                    for (ExchangeData data : exchangeDataList) {
                        if (data.getName().toLowerCase().contains(searchText.toLowerCase())) {
                            filteredList.add(data);
                        }
                    }
                }

                updateExchangelist(filteredList);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initExchanges() {
        exchangeDataList = new ArrayList<>();
        exchangeListAdapter = new ExchangeListAdapter(getContext(), this.exchangeDataList);
        lvExchangeList.setLayoutManager(new LinearLayoutManager(getContext()));
        lvExchangeList.setAdapter(exchangeListAdapter);
        marketStackService = new MarketStackService(this, BASE_URL_MARKETSTACK);
        getAllExcahnges();
    }

    private void getAllExcahnges() {
        BaseAsyncTask<Void, Void, BaseMarketstackData<ExchangeData>> exchangeTask =
                marketStackService.getExcahnges(this::setMarketstackExcahnges);
        exchangeTask.execute();
    }

    private void setMarketstackExcahnges(BaseMarketstackData<ExchangeData> data) {
        if (data.isSuccess()) {
            exchangeDataList = data.getData();
            tvExchangeSearch.setText("");
        } else {
            makeToast("Error while fetching Exchange list", Toast.LENGTH_LONG);
        }
    }

    private void updateExchangelist(List<ExchangeData> exchangeList) {
        this.exchangeListAdapter.updateList(exchangeList);
    }
}