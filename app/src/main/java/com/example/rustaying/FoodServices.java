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

public class FoodServices extends AppCompatActivity {

    private static final String TAG = "FoodServices";

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
        setContentView(R.layout.activity_food_services);

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
        RecyclerView recyclerView = findViewById(R.id.viewFoodServices);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FoodServiceAdapter adapter = new FoodServiceAdapter(this,serviceList);
        recyclerView.setAdapter(adapter);
    }


    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()) {

            Service info = new Service();

            info.setRequestType(data.getValue(Service.class).getRequestType());
            info.setRequestDate(data.getValue(Service.class).getRequestDate());
            info.setRequestedTimeFoodService(data.getValue(Service.class).getRequestedTimeFoodService());
            info.setInputs(data.getValue(Service.class).getInputs());
            info.setGardenSalad(data.getValue(Service.class).getGardenSalad());
            info.setTomatoSoup(data.getValue(Service.class).getTomatoSoup());
            info.setFriedChicken(data.getValue(Service.class).getFriedChicken());
            info.setCheesePizza(data.getValue(Service.class).getCheesePizza());
            info.setSpaghetti(data.getValue(Service.class).getSpaghetti());
            info.setMacAndCheese(data.getValue(Service.class).getMacAndCheese());
            info.setVanillaIceCream(data.getValue(Service.class).getVanillaIceCream());
            info.setFruitCake(data.getValue(Service.class).getFruitCake());
            info.setCoke(data.getValue(Service.class).getCoke());
            info.setSprite(data.getValue(Service.class).getSprite());
            info.setAppleJuice(data.getValue(Service.class).getAppleJuice());
            info.setFoodPrice(data.getValue(Service.class).getFoodPrice());

            //add object to array list
            //Food Service
            if (info.getRequestType().equals("Food Service")) {
                serviceList.add(new Service(
                        info.getRequestType(),
                        info.getRequestDate(),
                        info.getRequestedTimeFoodService(),
                        info.getInputs(),
                        info.getGardenSalad(),
                        info.getTomatoSoup(),
                        info.getFriedChicken(),
                        info.getCheesePizza(),
                        info.getSpaghetti(),
                        info.getMacAndCheese(),
                        info.getVanillaIceCream(),
                        info.getFruitCake(),
                        info.getCoke(),
                        info.getSprite(),
                        info.getAppleJuice(),
                        info.getFoodPrice()));
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