package com.example.rustaying;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;


public class BellboyServices extends AppCompatActivity{

    private static final String TAG = "BellboyServices";

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
        setContentView(R.layout.activity_bellboy_services);

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
     RecyclerView recyclerView = findViewById(R.id.viewServicesRecycleView);
     recyclerView.setLayoutManager(new LinearLayoutManager(this));
     ViewBellboyAdapter adapter = new ViewBellboyAdapter(this,serviceList);
     recyclerView.setAdapter(adapter);
    }


    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()) {

            Service info = new Service();

            //bellboy
            info.setRequestType(data.getValue(Service.class).getRequestType());
            info.setRequestDate(data.getValue(Service.class).getRequestDate());
            info.setLuggageValue(data.getValue(Service.class).getLuggageValue());
            info.setRequestedTimeBellboy(data.getValue(Service.class).getRequestedTimeBellboy());
            info.setFromWhere(data.getValue(Service.class).getFromWhere());
            info.setId(data.getValue(Service.class).getId());
            info.setStatus(data.getValue(Service.class).getStatus());
            Log.d(TAG, "ViewServiceClass: " + info.getRequestedTimeBellboy());

            //bellboy
            if (info.getRequestType().equals("Bellboy") && (info.getStatus().equals("Incomplete") || info.getStatus().equals("In Progress"))){
            serviceList.add(new Service(info.getRequestType(),info.getRequestDate(),
                    info.getLuggageValue(),info.getRequestedTimeBellboy(),info.getFromWhere(),
                    info.getStatus(), info.getId()));

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
