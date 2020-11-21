package com.rharshit.stocker.base.ui;

import android.content.Intent;
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
import com.rharshit.stocker.data.User;
import com.rharshit.stocker.ui.activity.LoginActivity;

import static com.rharshit.stocker.constant.DBConstants.REF_APP;
import static com.rharshit.stocker.constant.DBConstants.REF_USERS;
import static com.rharshit.stocker.constant.IntentConstants.GOOGLE_SIGNOUT;

public class BaseAppCompatLoggedinActivity extends BaseAppCompatActivity {

    private User user;

    private DatabaseReference userDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFirebase();
        initDatabases();
    }

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

    public void signout() {
        firebaseAuth.signOut();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.putExtra(GOOGLE_SIGNOUT, true);
        startActivity(intent);
        finish();
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

    public DatabaseReference getDatabase() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getDatabase(String ref) {
        return FirebaseDatabase.getInstance().getReference(ref);
    }
}
