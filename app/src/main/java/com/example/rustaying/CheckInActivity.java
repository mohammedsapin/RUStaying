/************************
 Authors:
 Shilp Shah
 Mathew Varghese
 *************************/

package com.example.rustaying;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CheckInActivity extends AppCompatActivity {

    private static final String TAG = "CheckInActivity";
    private TextView inDate, outDate;
    private Button checkInBtn, checkOutBtn;
    Calendar c;
    LocalDate currentDate, checkInDate, checkOutDate;
    Guest g = new Guest();

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_account:
                        Intent account = new Intent(CheckInActivity.this,ProfileActivity.class);
                        startActivity(account);
                        break;
                    case R.id.navigation_home:
                        Intent home = new Intent(CheckInActivity.this,HomeActivity.class);
                        startActivity(home);
                        break;
                    case R.id.navigation_services:
                        Intent service = new Intent(CheckInActivity.this, ServicesActivity.class);
                        startActivity(service);
                        break;
                }
                return false;
            }
        });

        mAuth = FirebaseAuth.getInstance(); //mAuth
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference(); //dbRef
        user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user != null){
                    userID = user.getUid();
                    //Log.d(TAG, "onAuthStateChanged: Signed In");
                }else{

                    //Log.d(TAG, "onAuthStateChanged: Signed out");
                }
            }
        };

        //Get guest information and initialize object
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

        //Get data from HomeActivity
        Intent i = getIntent();
        Bundle b = i.getBundleExtra("reservationDates");
        final String in = b.getString("checkInDate");
        final String out = b.getString("checkOutDate");

        inDate = (TextView) findViewById(R.id.inDate);
        outDate = (TextView) findViewById(R.id.outDate);
        checkInBtn = (Button) findViewById(R.id.guestCheckInBtn2);
        checkOutBtn = (Button) findViewById(R.id.guestCheckOutBtn);

        inDate.setText(in);
        outDate.setText(out);

        checkInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if the check in date is today's date, then allow them to check in
                c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                currentDate = parseDate(year, (month + 1), day); //Current date of LocalDate object
                checkInDate = parseStringDate(in);

                checkOutDate = parseStringDate(out);

                if(checkInDate.equals(currentDate))
                {
                    //Allow them to check in
                    updateCheckIn();
                    Toast.makeText(CheckInActivity.this, "Checked In!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CheckInActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(CheckInActivity.this, "You can check in on: " + in, Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CheckInActivity.this);

                //Check current date with check out date
                c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                LocalDate cDate = parseDate(year, (month+1), day);

                checkOutDate = parseStringDate(out);

                if(g.isCheckedIn() == false)
                {
                    alertDialog.setMessage("You have not checked in, do you want to cancel your reservation and check out?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    checkOutUpdate();
                                    Toast.makeText(CheckInActivity.this, "Reservation Cancelled",Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(CheckInActivity.this, HomeActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                }
                else if(cDate.equals(checkOutDate)) //if current date is equal the check out date
                {
                    alertDialog.setMessage("Do you want to check out of the hotel?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    checkOutUpdate();
                                    Toast.makeText(CheckInActivity.this, "Checked out!",Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(CheckInActivity.this, HomeActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                }
                else
                { //if current date is before to check out date

                    alertDialog.setMessage("Do you want to check out early of the hotel? (Your reservation is until " + checkOutDate.toString() + ")")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    checkOutUpdate();
                                    //Toast.makeText(CheckInActivity.this, "Checked out early!",Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(CheckInActivity.this, HomeActivity.class);
                                    startActivity(i);
                                    finish();

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                }

                AlertDialog alert = alertDialog.create();
                alert.setTitle("Check Out Confirmation");
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
    }

    private LocalDate parseDate(int year, int month, int date)
    {
        return LocalDate.of(year, month, date);
    }

    private void checkOutUpdate(){
        //Update room and guest information when user checks out

        //Guest has checked out so reset appropriate fields
        Map<String,Object> list = new HashMap<>();
        list.put("checkedIn", false);
        list.put("checkInDate", "");
        list.put("checkOutDate", "");
        list.put("roomNum", "");
        list.put("keyCode", "-1");

        myRef.child("Guest").child(userID).updateChildren(list).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    //Toast.makeText(CheckInActivity.this, "Guest updated", Toast.LENGTH_SHORT).show();
                    //Intent homeActivity = new Intent(CheckInActivity.this, HomeActivity.class);
                    //startActivity(homeActivity);
                    //finish();

                }else{
                    //startActivity(new Intent(EditInfoActivity.this, ProfileActivity.class));
                    //finish();
                }
            }
        });

        String temp;
        int roomInt = Integer.parseInt(g.getRoomNum());
        if(roomInt <= 9)
        {
            temp = "Room 0" + g.getRoomNum();
        }
        else
        {
            temp = "Room " + g.getRoomNum();
        }

        Map<String,Object> roomList = new HashMap<>();
        roomList.put("checkInDate", "");
        roomList.put("checkOutDate", "");
        roomList.put("checkedIn", false);
        roomList.put("isAvailable", true);


        //Updating room object information
        myRef.child("Rooms").child(temp).updateChildren(roomList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Toast.makeText(CheckInActivity.this, "Checked out!", Toast.LENGTH_SHORT).show();
                    //Intent homeActivity = new Intent(mCtx, HomeActivity.class);
                    //startActivity(homeActivity);

                    //mCtx.startActivity(homeActivity);

                }else{
                    Toast.makeText(CheckInActivity.this, "Error updating info", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(EditInfoActivity.this, ProfileActivity.class));
                    //finish();
                }
            }
        });
    }

    private void updateCheckIn()
    {
        Map<String,Object> list = new HashMap<>();
        list.put("checkedIn", true);

        myRef.child("Guest").child(userID).updateChildren(list).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Toast.makeText(CheckInActivity.this, "Guest updated", Toast.LENGTH_SHORT).show();
                    //Intent homeActivity = new Intent(CheckInActivity.this, HomeActivity.class);
                    //startActivity(homeActivity);
                    //finish();

                }else{
                    //startActivity(new Intent(EditInfoActivity.this, ProfileActivity.class));
                    //finish();
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
        g.setRoomNum(dataSnapshot.getValue(Guest.class).getRoomNum());
        g.setReservationMade(dataSnapshot.getValue(Guest.class).isReservationMade());
    }

    private LocalDate parseStringDate(String s)
    {
        String[] output = s.split("-");
        int[] numbers = new int[output.length];

        numbers[0] = Integer.parseInt(output[0]);
        numbers[1] = Integer.parseInt(output[1]);
        numbers[2] = Integer.parseInt(output[2]);

        return LocalDate.of(numbers[0], numbers[1], numbers[2]);
    }
}