package com.example.rustaying;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServicesActivity extends AppCompatActivity {

    private static final String TAG = "ServicesActivity";

    Button bellboy;
    Button travelvalet;
    Button maintenance;
    Button roomSerivce;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private String userID;
    Guest g = new Guest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        mAuth = FirebaseAuth.getInstance(); //mAuth
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference(); //dbRef
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_account:
                        Intent account = new Intent(ServicesActivity.this,ProfileActivity.class);
                        startActivity(account);
                        break;
                    case R.id.navigation_home:
                        Intent home = new Intent(ServicesActivity.this,HomeActivity.class);
                        startActivity(home);
                        break;
                    case R.id.navigation_services:
                        break;
                }
                return false;
            }
        });

        myRef.child("Guest").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        bellboy = (Button) findViewById(R.id.bellboybtn);
        bellboy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                 if(g.isCheckedIn())
                 {
                     Intent bu = new Intent(ServicesActivity.this, BellboyActivity.class);
                     startActivity(bu);
                 }
                 else{
                     Toast.makeText(ServicesActivity.this, "Please Check In to Make a Service " +
                                     "Request",
                             Toast.LENGTH_SHORT).show();
                 }

            }
        });
      
        travelvalet = (Button) findViewById(R.id.travelvaletbtn);
        travelvalet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(g.isCheckedIn())
                {
                    Intent bu = new Intent(ServicesActivity.this, ValetTravelActivity.class);
                    startActivity(bu);
                }
                else{
                    Toast.makeText(ServicesActivity.this, "Please Check In to Make a Service " +
                                    "Request",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        maintenance = (Button) findViewById(R.id.maintenancebtn);
        maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(g.isCheckedIn())
                {
                    Intent bu = new Intent(ServicesActivity.this, MaintenanceActivity.class);
                    startActivity(bu);
                }
                else{
                    Toast.makeText(ServicesActivity.this, "Please Check In to Make a Service " +
                                    "Request",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        roomSerivce = (Button) findViewById(R.id.roomServicebtn);
        roomSerivce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(g.isCheckedIn())
                {
                    Intent bu = new Intent(ServicesActivity.this, RoomServiceActivity.class);
                    startActivity(bu);
                }
                else{
                    Toast.makeText(ServicesActivity.this, "Please Check In to Make a Service " +
                                    "Request",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) {
        g.setCheckedIn(dataSnapshot.getValue(Guest.class).isCheckedIn());
    }
}