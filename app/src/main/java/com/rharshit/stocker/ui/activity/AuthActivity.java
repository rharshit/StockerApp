package com.rharshit.stocker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rharshit.stocker.base.ui.BaseAppCompatActivity;

import static android.content.ContentValues.TAG;
import static com.rharshit.stocker.constant.IntentConstants.AUTH_CREDENTIAL;
import static com.rharshit.stocker.constant.IntentConstants.FIREBASE_GOOGLE_AUTH;

public class AuthActivity extends BaseAppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFirebase();
    }

    private void initFirebase() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivityForResult(intent, FIREBASE_GOOGLE_AUTH);
        } else {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FIREBASE_GOOGLE_AUTH:
                try {
                    AuthCredential authCredential = data.getParcelableExtra(AUTH_CREDENTIAL);
                    firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Sign in successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Sign in failed", Toast.LENGTH_SHORT).show();
                            }
                            initFirebase();
                        }
                    });
                } catch (Exception e) {
                    Log.e(TAG, "onActivityResult: Error while setting user");
                    Toast.makeText(getContext(), "Failed to get user", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }
}