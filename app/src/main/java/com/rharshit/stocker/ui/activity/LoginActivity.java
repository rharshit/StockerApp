package com.rharshit.stocker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.rharshit.stocker.R;
import com.rharshit.stocker.base.ui.BaseAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;
import static com.rharshit.stocker.constant.IntentConstants.GOOGLE_SIGNOUT;
import static com.rharshit.stocker.constant.IntentConstants.GOOGLE_SIGN_IN_CODE;

public class LoginActivity extends BaseAppCompatActivity {

    @BindView(R.id.b_google_signin)
    SignInButton googleSignin;
    private GoogleSignInClient googleSignInClient;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        googleSignin.setOnClickListener(this::googleSignin);
        init();
        performIntentAction();
    }

    private void performIntentAction() {
        Intent intent = getIntent();

        boolean googleSignout = intent.getBooleanExtra(GOOGLE_SIGNOUT, false);
        intent.removeExtra(GOOGLE_SIGNOUT);
        if (googleSignout) {
            googleSignInClient.signOut().addOnCompleteListener(task -> {
                Toast.makeText(getContext(), "Signed out", Toast.LENGTH_SHORT).show();
                init();
            });
        }
    }

    private void init() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void googleSignin(View view) {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, GOOGLE_SIGN_IN_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GOOGLE_SIGN_IN_CODE:
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    AuthCredential authCredential = GoogleAuthProvider
                            .getCredential(account.getIdToken(), null);

                    Toast.makeText(getContext(), "Signing in", Toast.LENGTH_SHORT).show();

                    firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Toast.makeText(getContext(), "Sign in successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Sign in failed", Toast.LENGTH_SHORT).show();
                        }
                        init();
                    });
                } catch (ApiException e) {
                    Log.e(TAG, "onActivityResult: Error while Signing in to google", e);
                    Toast.makeText(getContext(), "Sign in failed", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}