package com.rharshit.stocker.ui.activity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rharshit.stocker.R;
import com.rharshit.stocker.StockerApplication;
import com.rharshit.stocker.base.rx.BaseAsyncTask;
import com.rharshit.stocker.base.ui.BaseAppCompatLoggedinActivity;
import com.rharshit.stocker.base.ui.BaseNavigationView;
import com.rharshit.stocker.base.widgets.BaseToolbar;
import com.rharshit.stocker.data.BaseMarketstackData;
import com.rharshit.stocker.data.ExchangeData;
import com.rharshit.stocker.data.TickerData;
import com.rharshit.stocker.service.MarketStackService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rharshit.stocker.constant.APIConstants.BASE_URL_MARKETSTACK;
import static com.rharshit.stocker.constant.Constants.MAX_MARKETSTACK_LIMIT;

public class MainActivity extends BaseAppCompatLoggedinActivity {

    @BindView(R.id.nav_view)
    BaseNavigationView navigationView;

    @BindView(R.id.cl_exchange_main_display)
    ConstraintLayout exchangeMainDisplay;

    @BindView(R.id.tv_exchange_name_main)
    TextView exchangeName;

    @BindView(R.id.tv_exchange_currency_main)
    TextView exchangeCurrency;

    public static final String LOADING_TICKERS = "Fetching Ticker data";
    private MarketStackService marketStackService;
    private ExchangeData exchangeData;

    private AppBarConfiguration mAppBarConfiguration;
    private List<TickerData> tickerDataList;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);

        BaseToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dashboard, R.id.nav_stocks, R.id.nav_portfolio)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUserDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initExchange();
    }

    private void initExchange() {
        ExchangeData exchangeData = ((StockerApplication) getApplication()).getExchangeData();
        if (exchangeData == null) {
            selectExchange();
        } else {
            this.exchangeData = exchangeData;
            exchangeName.setText(exchangeData.getName());
            exchangeCurrency.setText(exchangeData.getCurrency().toString());
            exchangeMainDisplay.setOnClickListener(v -> selectExchange());
            initTickers();
        }

    }

    private void initTickers() {
        tickerDataList = ((StockerApplication) getApplication()).getTickerDataList();
        if (tickerDataList == null || tickerDataList.isEmpty()) {
            setTickerData();
        }
    }

    private void setTickerData() {
        addLoading(LOADING_TICKERS);
        if (marketStackService == null) {
            marketStackService = new MarketStackService(this, BASE_URL_MARKETSTACK);
        }
        BaseAsyncTask<Void, Void, BaseMarketstackData<TickerData>> tickerTask =
                marketStackService.getTickers(this.exchangeData.getMic(), this::onFinishTickerFetch);
        tickerTask.execute();
    }

    private synchronized void onFinishTickerFetch(BaseMarketstackData<TickerData> tickerDataBaseMarketstackData) {
        if (!tickerDataBaseMarketstackData.isSuccess()) {
            makeToast("Error while fetching ticker data", Toast.LENGTH_SHORT);
            removeLoading(LOADING_TICKERS);
            return;
        }

        int offset = tickerDataBaseMarketstackData.getOffset();
        int limit = MAX_MARKETSTACK_LIMIT;
        int total = tickerDataBaseMarketstackData.getTotal();

        if (offset == 0) {
            this.tickerDataList = tickerDataBaseMarketstackData.getData();
            int currOffset = offset;
            while (currOffset + limit < total) {
                currOffset = currOffset + limit;
                BaseAsyncTask<Void, Void, BaseMarketstackData<TickerData>> tickerTask =
                        marketStackService.getTickers(this.exchangeData.getMic(), currOffset, this::onFinishTickerFetch);
                tickerTask.execute();
            }
        } else {
            this.tickerDataList.addAll(tickerDataBaseMarketstackData.getData());
        }

        if (this.tickerDataList.size() == total) {
            ((StockerApplication) getApplication()).setTickerDataList(this.tickerDataList);
            removeLoading(LOADING_TICKERS);
        }
    }

    private void selectExchange() {
        startActivity(new Intent(getContext(), ExchangeActivity.class));
    }

    private void setUserDetails() {
        ImageView userIcon = navigationView.getHeaderView(0).findViewById(R.id.nav_user_pic);
        TextView tvUserName = navigationView.getHeaderView(0).findViewById(R.id.nav_user_name);
        TextView tvUserEmail = navigationView.getHeaderView(0).findViewById(R.id.nav_user_email);

        RequestOptions options = new RequestOptions()
                .override(userIcon.getWidth(), userIcon.getHeight())
                .circleCrop();
        Glide.with(getContext())
                .setDefaultRequestOptions(options)
                .load(getFirebaseUser().getPhotoUrl())
                .into(userIcon);

        tvUserName.setText(getUserName());
        tvUserEmail.setText(getUserEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_signout:
                signout();
                return true;
        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}