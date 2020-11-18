package com.rharshit.stocker.base.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rharshit.stocker.ui.activity.LoginActivity;

import static com.rharshit.stocker.constant.IntentConstants.GOOGLE_SIGNOUT;

public class BaseAppCompatLoggedinActivity extends BaseAppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFirebase();
    }

    private void initFirebase() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void signout() {
        firebaseAuth.signOut();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.putExtra(GOOGLE_SIGNOUT, true);
        startActivity(intent);
        finish();
    }

    public FirebaseUser getUser() {
        return firebaseUser;
    }

    public String getUserName() {
        return getUser().getDisplayName();
    }

    public String getUserEmail() {
        return getUser().getEmail();
    }
}
