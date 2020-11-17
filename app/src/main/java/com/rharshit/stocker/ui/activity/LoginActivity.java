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
import com.google.firebase.auth.GoogleAuthProvider;
import com.rharshit.stocker.R;
import com.rharshit.stocker.base.ui.BaseAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;
import static com.rharshit.stocker.constant.IntentConstants.AUTH_CREDENTIAL;
import static com.rharshit.stocker.constant.IntentConstants.GOOGLE_SIGN_IN_CODE;

public class LoginActivity extends BaseAppCompatActivity {

    @BindView(R.id.b_google_signin)
    SignInButton googleSignin;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        googleSignin.setOnClickListener(this::googleSignin);
        googleSignin(null);
    }

    private void googleSignin(View view) {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);

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

                    Intent intent = new Intent();
                    intent.putExtra(AUTH_CREDENTIAL, authCredential);
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (ApiException e) {
                    Log.e(TAG, "onActivityResult: Error while Signing in to google", e);
                    Toast.makeText(getContext(), "Sign in failed", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}