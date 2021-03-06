package com.rharshit.stocker.ui.activity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.rharshit.stocker.base.ui.BaseAppCompatLoggedinActivity;
import com.rharshit.stocker.base.ui.BaseNavigationView;
import com.rharshit.stocker.base.widgets.BaseToolbar;
import com.rharshit.stocker.data.ExchangeData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseAppCompatLoggedinActivity {

    @BindView(R.id.nav_view)
    BaseNavigationView navigationView;

    @BindView(R.id.cl_exchange_main_display)
    ConstraintLayout exchangeMainDisplay;

    @BindView(R.id.tv_exchange_name_main)
    TextView exchangeName;

    @BindView(R.id.tv_exchange_currency_main)
    TextView exchangeCurrency;

    private AppBarConfiguration mAppBarConfiguration;

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
            selectExcahnge();
        } else {
            exchangeName.setText(exchangeData.getName());
            exchangeCurrency.setText(exchangeData.getCurrency().toString());
            exchangeMainDisplay.setOnClickListener(v -> selectExcahnge());
        }
    }

    private void selectExcahnge() {
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