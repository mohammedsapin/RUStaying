/************************
 Authors:
 Keya Patel
 Zain Sayed
 *************************/

package com.example.rustaying;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class InboxActivity extends AppCompatActivity{

    private static final String TAG = "InboxActivity";

    //Services List
    private ArrayList<Service> serviceList = new ArrayList<>();

    //Firebase
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_account:
                        Intent account = new Intent(InboxActivity.this,ProfileActivity.class);
                        startActivity(account);
                        finish();
                        break;
                    case R.id.navigation_home:
                        Intent home = new Intent(InboxActivity.this,HomeActivity.class);
                        startActivity(home);
                        finish();
                        break;
                    case R.id.navigation_services:
                        Intent service = new Intent(InboxActivity.this, ServicesActivity.class);
                        startActivity(service);
                        finish();
                        break;
                }
                return false;
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged: Signed In");
                } else {
                    Log.d(TAG, "onAuthStateChanged: Signed out");
                }
            }
        };

        myRef.child("Service").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void createRecycleView(){
        Log.d(TAG, "createRecycleView: Started view");
        RecyclerView recyclerView = findViewById(R.id.inboxActivityRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        InboxActivityAdapter adapter = new InboxActivityAdapter(this,serviceList);
        recyclerView.setAdapter(adapter);
    }


    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()){
            Service info = new Service();

            String serviceID = data.getKey();

            if(userID.equals(serviceID))
            {
                Log.d(TAG, "showData: " + userID + "     " + serviceID);
                for (DataSnapshot snapshot : data.getChildren()) {
                    Log.d(TAG, "Inbox1: =============================" + snapshot.getKey());
                    for (DataSnapshot snapshot2 : snapshot.getChildren()){
                        String requestID=snapshot2.getKey();
                        Log.d(TAG, "Inbox2: =============================" + snapshot2.getKey());
                        if(snapshot2.getKey().equals("requestType"))
                        {
                            info.setRequestType(snapshot.getValue(Service.class).getRequestType());
                            Log.d(TAG, "showData: " + "setting value1");
                        }
                        if(snapshot2.getKey().equals("status"))
                        {
                            info.setStatus(snapshot.getValue(Service.class).getStatus());
                            Log.d(TAG, "showData: " + "setting value2");
                        }
                        if(snapshot2.getKey().equals("requestDate")){
                            info.setRequestDate(snapshot.getValue(Service.class).getRequestDate());
                            Log.d(TAG, "showData: " + "setting value3");
                        }

                            if (userID.equals(serviceID)){
                                Log.d(TAG, "showData: " + "Inside if statement");

                            }

                    }
                    serviceList.add(new Service(info.getRequestType(),info.getStatus(),info.getRequestDate()));
                }
            }


            createRecycleView();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
