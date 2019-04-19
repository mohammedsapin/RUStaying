package com.example.rustaying;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewServices extends AppCompatActivity {

    private static final String TAG = "ViewServices";

    //Services List
    private ArrayList<Service> serviceList = new ArrayList<>();

    SwipeController swipeController = null;

    //Firebase
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_services);

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
     final ViewServicesAdapter adapter = new ViewServicesAdapter(this,serviceList);
     recyclerView.setAdapter(adapter);

     swipeController = new SwipeController(new SwipeControllerActions() {
         @Override
         public void onRightClicked(int position) {
             super.onRightClicked(position);
         }
     });

     ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
     itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()) {

            Service info = new Service();

            info.setRequestType(data.getValue(Service.class).getRequestType());
            info.setLuggageValue(data.getValue(Service.class).getLuggageValue());
            info.setRequestedTime(data.getValue(Service.class).getRequestedTime());

            //add object to array list
            serviceList.add(new Service(info.getRequestType(),info.getLuggageValue(),info.getRequestedTime()));

            Log.d(TAG, "showData: " + info.getRequestType());
            Log.d(TAG, "showData: " + info.getLuggageValue());
            Log.d(TAG, "showData: " + info.getRequestedTime());
            Log.d(TAG, "showData: Array List: " + info);

            //add array list to recycle view
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
