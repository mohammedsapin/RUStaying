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

    //firebase stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    //XML attributes
    private ListView guestInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //XML objects
        guestInfoList = (ListView) findViewById(R.id.guestInfo);

        //Firebase Objects
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user != null){
                    Log.d(TAG, "onAuthStateChanged: " + userID);
                }else {
                    Log.d(TAG, "onAuthStateChanged: Signed out");
                }
            }
        };

        myRef.child("Guests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_account:
                        break;
                    case R.id.navigation_home:
                        Intent home = new Intent(ProfileActivity.this,HomeActivity.class);
                        startActivity(home);
                        break;
                    case R.id.navigation_services:
                        Intent services = new Intent(ProfileActivity.this,ServicesActivity.class);
                        startActivity(services);
                        break;
                }
                return false;
            }
        });
    }

    private void showData(DataSnapshot dataSnapshot){
        for (DataSnapshot data : dataSnapshot.getChildren()){
            Guest info = new Guest();
            info.setFirstName(data.child(userID).getValue(Guest.class).getFirstName()); // set first name
            info.setLastName(data.child(userID).getValue(Guest.class).getLastName()); // set last name
            info.setGuestEmail(data.child(userID).getValue(Guest.class).getGuestEmail()); // set email

            Log.d(TAG, "showData: First Name: " + info.getFirstName());
            Log.d(TAG, "showData: Last Name: " + info.getLastName());
            Log.d(TAG, "showData: Guest Email: " + info.getGuestEmail());

            ArrayList<String> guestInfo = new ArrayList<>();

            guestInfo.add(info.getFirstName());
            guestInfo.add(info.getLastName());
            guestInfo.add(info.getGuestEmail());

            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,guestInfo);

            guestInfoList.setAdapter(adapter);

        }
    }
}
