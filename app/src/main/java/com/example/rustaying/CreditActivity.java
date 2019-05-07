/************************
 Authors:
 Keya Patel
 Zain Sayed
 *************************/

package com.example.rustaying;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreditActivity extends AppCompatActivity {

    private static final String TAG = "CreditActivity";
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth auth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userID;
    private Guest g = new Guest();
    private Button Edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_account:
                        Intent account = new Intent(CreditActivity.this,ProfileActivity.class);
                        startActivity(account);
                        break;
                    case R.id.navigation_home:
                        Intent home = new Intent(CreditActivity.this,HomeActivity.class);
                        startActivity(home);
                        break;
                    case R.id.navigation_services:
                        Intent services = new Intent(CreditActivity.this, ServicesActivity.class);
                        startActivity(services);
                        break;
                }
                return false;
            }
        });

        //mListView=(ListView)findViewById(R.id.guestInfo);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        myRef=mFirebaseDatabase.getReference();
        FirebaseUser user = auth.getCurrentUser();
        userID = user.getUid();

        Edit = (Button) findViewById(R.id.creditInfo);
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent creditInfo = new Intent(CreditActivity.this,CreditInfoActivity.class);
                startActivity(creditInfo); //Redirect to feedback page
                finish();
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user==null){
                    startActivity(new Intent(CreditActivity.this, LoginActivity.class));
                    Log.d(TAG, "onAuthStateChanged: Signed out");
                    finish();
                }else{

                    Log.d(TAG,"onAuthStateChanged:signed in");
                    myRef.child("Guest").child(userID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            showData(dataSnapshot);

                            TextView verification = (TextView) findViewById(R.id.CCV);
                            String verif = g.getCCV();
                            verification.setText("***");

                            TextView number = (TextView) findViewById(R.id.creditCardNumber);
                            String lastName = g.getCreditCardNumber();
                            number.setText(lastName);

                            TextView name = (TextView) findViewById(R.id.nameOnCard);
                            String newName = g.getNameOnCCard();
                            name.setText(newName);

                            TextView date = (TextView) findViewById(R.id.expDate);
                            String expDate = g.getExpirationDate();
                            date.setText(expDate);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        };
    }

    private void showData(DataSnapshot dataSnapshot) {
        g.setExpirationDate(dataSnapshot.getValue(Guest.class).getExpirationDate());
        g.setNameOnCCard(dataSnapshot.getValue(Guest.class).getNameOnCCard());
        g.setCCV(dataSnapshot.getValue(Guest.class).getCCV());
        g.setCreditCardNumber(dataSnapshot.getValue(Guest.class).getCreditCardNumber());
    }

    @Override
    public void onStart(){
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }
    @Override
    public void onStop(){
        super.onStop();
        auth.removeAuthStateListener(authStateListener);
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
