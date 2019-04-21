package com.example.rustaying;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    private Button logout, bkRmBtn, feedbackBtn, checkInBtn;
    Guest g = new Guest(); //Guest object for user

    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth auth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_account:
                        Intent account = new Intent(HomeActivity.this,ProfileActivity.class);
                        startActivity(account);
                        break;
                    case R.id.navigation_home:
                        break;
                    case R.id.navigation_services:
                        Intent services = new Intent(HomeActivity.this,ServicesActivity.class);
                        startActivity(services);
                        break;
                }
                return false;
            }
        });

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();


        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = mFirebaseDatabase.getReference();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user == null){
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    Log.d(TAG, "onAuthStateChanged: Signed out");
                    finish();
                }
                else
                {
                    String userID = user.getUid();
                    myRef.child("Guest").child(userID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            initGuestObject(dataSnapshot); //Initializing Object works

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        };

        logout = (Button) findViewById(R.id.logoutBtn);

        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Creating dialog box to confirm if user wants to logout
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
                alertDialog.setMessage("Do you want to logout?")
                        //Positive button is Yes, meaning the use wants to logout
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        })
                        //Negative button is No, meaning user does not want to logout
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel(); //Close dialog box. Nothing happens
                            }
                        });


                    AlertDialog alert = alertDialog.create();
                    alert.setTitle("Confirm Logout");
                    alert.show();

                    //Changing colors of the Yes and No buttons
                    Button negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                    negativeButton.setTextColor(Color.RED);
                    negativeButton.setBackgroundColor(Color.WHITE);

                    Button positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                    positiveButton.setTextColor(Color.RED);
                    positiveButton.setBackgroundColor(Color.WHITE);
            }
        });

        bkRmBtn = (Button) findViewById(R.id.bkRmBtn);
        bkRmBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent bookRoomPage = new Intent(HomeActivity.this, ReservationActivity.class);
                startActivity(bookRoomPage); //Redirect to list of all rooms
            }
        }
        );

        feedbackBtn = (Button)findViewById(R.id.feedbackBtn);
        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent feedback = new Intent(HomeActivity.this,FeedbackActivity.class);
                startActivity(feedback); //Redirect to feedback page
            }
        });

        checkInBtn = (Button)findViewById(R.id.checkInBtn);
        checkInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                //On button click, check the check in and check out dates
                if(g.getCheckInDate().equals("") || g.getCheckInDate() == null
                        || g.getCheckOutDate().equals("") || g.getCheckOutDate() == null) //Means no reservation made
                {
                    Toast.makeText(HomeActivity.this, "You have no reservations",Toast.LENGTH_SHORT).show();
                }

                else //If there is reservation, setup intent and send checkIn and checkOut date to new activity
                {
                    Intent checkInAct = new Intent(HomeActivity.this, CheckInActivity.class);

                    //Set up Bundle to send checkIn and checkOut dates to CheckInActivity
                    Bundle b = new Bundle();
                    b.putString("checkInDate", g.getCheckInDate());
                    b.putString("checkOutDate", g.getCheckOutDate());


                    checkInAct.putExtra("reservationDates", b);
                    startActivity(checkInAct); //Redirect to check in page

                }
            }
        });

    }

    private void initGuestObject(DataSnapshot dataSnapshot)
    {
            //Setting fields of guest object
            g.setFirstName(dataSnapshot.getValue(Guest.class).getFirstName());
            g.setLastName(dataSnapshot.getValue(Guest.class).getLastName());
            g.setCheckedIn(dataSnapshot.getValue(Guest.class).isCheckedIn());
            g.setAccountStatus(dataSnapshot.getValue(Guest.class).isAccountStatus());
            g.setLuggage(dataSnapshot.getValue(Guest.class).getLuggage());
            g.setCheckInDate(dataSnapshot.getValue(Guest.class).getCheckInDate());
            g.setCheckOutDate(dataSnapshot.getValue(Guest.class).getCheckOutDate());

    }

    public void logout(){
        Toast.makeText(HomeActivity.this,"Logged Out",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        finish();
        auth.signOut();
    }

    @Override
    public void onStart(){
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if (authStateListener != null){
            auth.removeAuthStateListener(authStateListener);
        }
    }
}
