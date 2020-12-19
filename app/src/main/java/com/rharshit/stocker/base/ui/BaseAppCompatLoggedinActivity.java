package com.rharshit.stocker.base.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rharshit.stocker.base.rx.BaseAsyncTask;
import com.rharshit.stocker.data.User;
import com.rharshit.stocker.service.ZerodhaService;
import com.rharshit.stocker.ui.activity.LoginActivity;
import com.rharshit.stocker.ui.activity.ZerodhaLogin;
import com.zerodhatech.kiteconnect.KiteConnect;

import static com.rharshit.stocker.constant.APIConstants.ZERODHA_API_KEY;
import static com.rharshit.stocker.constant.APIConstants.ZERODHA_USER_ID;
import static com.rharshit.stocker.constant.DBConstants.REF_APP;
import static com.rharshit.stocker.constant.DBConstants.REF_USERS;
import static com.rharshit.stocker.constant.DBConstants.ZERODHA_SHARED_PREF;
import static com.rharshit.stocker.constant.IntentConstants.GOOGLE_SIGNOUT;
import static com.rharshit.stocker.constant.IntentConstants.ZERODHA_LOGIN_URI;
import static com.rharshit.stocker.constant.IntentConstants.ZERODHA_REQUEST_CODE;
import static com.rharshit.stocker.constant.IntentConstants.ZERODHA_REQUEST_TOKEN;
import static com.rharshit.stocker.constant.SharedPreferenceConstants.KEY_ZERODHA_REQUEST_TOKEN;

public abstract class BaseAppCompatLoggedinActivity extends BaseAppCompatActivity {

    private User user;

    private static com.zerodhatech.models.User zerodhaUser;
    private static KiteConnect kiteSdk;

    private DatabaseReference userDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private ZerodhaService zerodhaService;

    public static KiteConnect getKiteSdk() {
        if (kiteSdk == null) {
            kiteSdk = new KiteConnect(ZERODHA_API_KEY);
        }
        kiteSdk.setUserId(ZERODHA_USER_ID);
        return kiteSdk;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFirebase();
        initApis();
        initDatabases();
    }

    protected void initApis() {
        initZerodha();
    }

    protected void initZerodha() {
        SharedPreferences sharedPreferences = getContext()
                .getSharedPreferences(ZERODHA_SHARED_PREF, Context.MODE_PRIVATE);
        String requestToken = sharedPreferences.getString(KEY_ZERODHA_REQUEST_TOKEN, null);

        initZerodha(requestToken);
    }

    protected void initZerodha(String requestToken) {
        if (requestToken == null) {
            getZerodhaRequestToken();
        } else {
            BaseAsyncTask<String, Void, com.zerodhatech.models.User> task = getZerodhaService().getUserTask(this::onInitZerodha);
            task.execute(requestToken);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ZERODHA_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    String requestToken = data.getStringExtra(ZERODHA_REQUEST_TOKEN);
                    initZerodha(requestToken);
                } else {
                    makeToast("Error while retreiving request token", Toast.LENGTH_SHORT);
                    finish();
                }
                break;
        }
    }

    public void onInitZerodha(com.zerodhatech.models.User user) {
        if (user == null) {
            getZerodhaRequestToken();
        } else {
            setZerodhaTokens(user);
            zerodhaUser = user;
        }
    }

    private void getZerodhaRequestToken() {
        String url = getKiteSdk().getLoginURL();
        Intent zerodhaLoginIntent = new Intent(getContext(), ZerodhaLogin.class);
        zerodhaLoginIntent.putExtra(ZERODHA_LOGIN_URI, url);
        startActivityForResult(zerodhaLoginIntent, ZERODHA_REQUEST_CODE);
//        Intent loginIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        startActivityForResult(loginIntent, ZERODHA_REQUEST_CODE);
    }

    @Override
    public abstract void init();

    private void initDatabases() {
        userDatabaseReference = getDatabase(REF_APP).child(REF_USERS).child(user.getUserUid());

        ValueEventListener userValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean exists = snapshot.exists();

                if (exists) {
                    User userFb = snapshot.getValue(User.class);
                    setUser(userFb);
                } else {
                    createUser(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                makeToast("Cancelled fetching user details", Toast.LENGTH_SHORT);
            }
        };

        userDatabaseReference.addValueEventListener(userValueEventListener);
    }

    private void initFirebase() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            user = new User(getFirebaseUser().getDisplayName(),
                    getFirebaseUser().getEmail(),
                    getFirebaseUser().getUid());
        }
    }

    private void createUser(User user) {
        getDatabase(REF_APP).child(REF_USERS).child(user.getUserUid()).setValue(user);
    }

    private void setZerodhaTokens(com.zerodhatech.models.User user) {
        getKiteSdk().setAccessToken(user.accessToken);
        getKiteSdk().setPublicToken(user.publicToken);
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public User getUser() {
        return user;
    }

    private void setUser(User user) {
        this.user = user;
    }

    public String getUserName() {
        return getUser().getUserName();
    }

    public String getUserEmail() {
        return getUser().getUserEmail();
    }

    public void signout() {
        firebaseAuth.signOut();
        getZerodhaService().invalidateSession();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.putExtra(GOOGLE_SIGNOUT, true);
        startActivity(intent);
        finish();
    }

    public ZerodhaService getZerodhaService() {
        if (zerodhaService == null) {
            zerodhaService = new ZerodhaService(this, getKiteSdk());
        }
        return zerodhaService;
    }

    public DatabaseReference getDatabase() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getDatabase(String ref) {
        return FirebaseDatabase.getInstance().getReference(ref);
    }
}
