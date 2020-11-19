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
import com.rharshit.stocker.data.Portfolio;
import com.rharshit.stocker.data.User;
import com.rharshit.stocker.ui.activity.LoginActivity;

import static com.rharshit.stocker.constant.DBConstants.REF_APP;
import static com.rharshit.stocker.constant.DBConstants.REF_USERS;
import static com.rharshit.stocker.constant.DBConstants.REF_USER_DATA;
import static com.rharshit.stocker.constant.IntentConstants.GOOGLE_SIGNOUT;

public class BaseAppCompatLoggedinActivity extends BaseAppCompatActivity {

    private User user;
    private Portfolio portfolio;

    private DatabaseReference userDatabaseReference;
    private DatabaseReference portfolioDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFirebase();
        initDatabases();
//        checkUserExists(user);
//        checkPortfolioExists(user);
    }

    private void initDatabases() {
        userDatabaseReference = getDatabase(REF_APP).child(REF_USERS).child(user.getUserUid());
        portfolioDatabaseReference = getDatabase(REF_APP).child(REF_USER_DATA).child(user.getUserUid());

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

        ValueEventListener portfolioValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean exists = snapshot.exists();

                if (exists) {
                    Portfolio portfolioFb = snapshot.getValue(Portfolio.class);
                    setPortfolio(portfolioFb);
                } else {
                    createPortfolioForuser(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                makeToast("Cancelled fetching user portfolio", Toast.LENGTH_SHORT);
            }
        };

        userDatabaseReference.addValueEventListener(userValueEventListener);
        portfolioDatabaseReference.addValueEventListener(portfolioValueEventListener);
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
//
//    private void checkUserExists(User user) {
//        checkUserExists(user, 1);
//    }
//
//    private void checkUserExists(User user, int tries) {
//        if (tries > THRESHOLD_TRIES) {
//            return;
//        }
//        userDatabaseReference
//        getDatabase(REF_APP)
//                .child(REF_USERS)
//                .child(user.getUserUid())
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        boolean exists = snapshot.exists();
//
//                        if (exists) {
//                            User userFb = snapshot.getValue(User.class);
//                            setUser(userFb);
//                        } else {
//                            createUser(user);
//                            checkUserExists(user, tries + 1);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(getContext(), "Cancelled fetching user details", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    private void checkPortfolioExists(User user) {
//        checkPortfolioExists(user, 1);
//    }
//
//    private void checkPortfolioExists(User user, int tries) {
//        if (tries > THRESHOLD_TRIES) {
//            return;
//        }
//        getDatabase(REF_APP)
//                .child(REF_USER_DATA)
//                .child(user.getUserUid())
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        boolean exists = snapshot.exists();
//
//                        if (exists) {
//                            Portfolio portfolioFb = snapshot.getValue(Portfolio.class);
//                            setPortfolio(portfolioFb);
//                        } else {
//                            createPortfolioForuser(user);
//                            checkPortfolioExists(user, tries + 1);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(getContext(), "Cancelled fetching user portfolio", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    private void createUser(User user) {
        getDatabase(REF_APP).child(REF_USERS).child(user.getUserUid()).setValue(user);
    }

    private void createPortfolioForuser(User user) {
        Portfolio portfolio = new Portfolio();
        portfolio.getPortfolioStockData().add(new Portfolio.PortfolioStockData("Initialize", 0, 0));
        getDatabase(REF_APP).child(REF_USER_DATA).child(user.getUserUid()).setValue(portfolio);
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

    public Portfolio getPortfolio() {
        return portfolio;
    }

    private void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
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
