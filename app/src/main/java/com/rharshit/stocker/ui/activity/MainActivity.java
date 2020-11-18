package com.rharshit.stocker.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.rharshit.stocker.R;
import com.rharshit.stocker.base.ui.BaseAppCompatLoggedinActivity;
import com.rharshit.stocker.base.ui.BaseNavigationView;
import com.rharshit.stocker.base.widgets.BaseToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseAppCompatLoggedinActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @BindView(R.id.nav_view)
    BaseNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dashboard, R.id.nav_stocks, R.id.nav_portfolio)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUserDetails();
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
                .load(getUser().getPhotoUrl())
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