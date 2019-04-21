package com.example.rustaying;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth auth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userID;
    private Guest g = new Guest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //mListView=(ListView)findViewById(R.id.guestInfo);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        myRef=mFirebaseDatabase.getReference();
        FirebaseUser user = auth.getCurrentUser();
        userID = user.getUid();



        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user==null){
                    startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                    Log.d(TAG, "onAuthStateChanged: Signed out");
                    finish();
                }else{

                    Log.d(TAG,"onAuthStateChanged:signed in");
                    myRef.child("Guest").child(userID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            showData(dataSnapshot);

                            TextView fname2 = (TextView) findViewById(R.id.fname2);
                            String firstName = g.getFirstName();
                            fname2.setText(firstName);

                            TextView lname2 = (TextView) findViewById(R.id.lname2);
                            String lastName = g.getLastName();
                            lname2.setText(lastName);

                            TextView email2 = (TextView) findViewById(R.id.email2);
                            String email = g.getGuestEmail();
                            email2.setText(email);
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
            g.setFirstName(dataSnapshot.getValue(Guest.class).getFirstName());
            g.setLastName(dataSnapshot.getValue(Guest.class).getLastName());
            g.setCheckedIn(dataSnapshot.getValue(Guest.class).isCheckedIn());
            g.setGuestEmail(dataSnapshot.getValue(Guest.class).getGuestEmail());
            g.setAccountStatus(dataSnapshot.getValue(Guest.class).isAccountStatus());
            g.setLuggage(dataSnapshot.getValue(Guest.class).getLuggage());
            g.setCheckInDate(dataSnapshot.getValue(Guest.class).getCheckInDate());
            g.setCheckOutDate(dataSnapshot.getValue(Guest.class).getCheckOutDate());
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
