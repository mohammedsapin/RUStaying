package com.example.rustaying;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ValetServices extends AppCompatActivity {

    private static final String TAG = "ValetServices";

    //Services List
    private ArrayList<Service> serviceList = new ArrayList<>();

    //Firebase
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valet_services);

        createRecycleView();

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

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

        myRef.child("Service").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                showData(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                showData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void createRecycleView(){
        Log.d(TAG, "createRecycleView: Started view");
        RecyclerView recyclerView = findViewById(R.id.viewValetServices);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ValetServicesAdapter adapter = new ValetServicesAdapter(this,serviceList);
        recyclerView.setAdapter(adapter);
    }


    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()) {

            Service info = new Service();

            info.setRequestType(data.getValue(Service.class).getRequestType());
            info.setRequestedTimeValet(data.getValue(Service.class).getRequestedTimeValet());
            info.setRequestDate(data.getValue(Service.class).getRequestDate());
            info.setStartingStreet(data.getValue(Service.class).getStartingStreet());
            info.setStartingCityStateZip(data.getValue(Service.class).getStartingCityStateZip());
            info.setDestinationStreet(data.getValue(Service.class).getDestinationStreet());
            info.setDestinationCityStateZip(data.getValue(Service.class).getDestinationCityStateZip());
            info.setNumberTraveling(data.getValue(Service.class).getNumberTraveling());

            //add object to array list
            //valet
            if (info.getRequestType().equals("ValetTravel")) {
                serviceList.add(new Service(info.getRequestType(), info.getRequestedTimeValet(), info.getRequestDate(),
                        info.getStartingStreet(), info.getDestinationStreet(),info.getStartingCityStateZip(),
                         info.getDestinationCityStateZip(),info.getNumberTraveling(),info.getTemp1(),info.getTemp2()));

                //add array list to recycle view
                createRecycleView();
            }
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