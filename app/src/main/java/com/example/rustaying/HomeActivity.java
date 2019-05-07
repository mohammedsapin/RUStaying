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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

public class HomeActivity extends AppCompatActivity {
    private Guest g = new Guest();
    private static final String TAG = "HomeActivity";

    private Button logout, bkRmBtn, feedbackBtn, checkInBtn, keyBtn, inboxBtn;


    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth auth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    String userID;

    int year0Tok;
    int month0Tok;
    int day0Tok;
    LocalDate inDateTok;

    int year1Tok;
    int month1Tok;
    int day1Tok;
    LocalDate outDateTok;

    int year2Tok;
    int month2Tok;
    int day2Tok;
    LocalDate outDateTok1;

    Calendar c;
    LocalDate currentDate;

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

        c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        currentDate = parseDate(year,(month+1), day);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();


        auth = FirebaseAuth.getInstance();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        myRef=mFirebaseDatabase.getReference();
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
                    userID = user.getUid();
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

        updateDataForToday(); //Update room and guest information based on current date

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


        inboxBtn = (Button)findViewById(R.id.inboxBtn);
        inboxBtn.setOnClickListener(new View.OnClickListener()
             {
                 @Override
                 public void onClick(View v)
                 {
                     final Boolean check1 = g.isCheckedIn();
                     if(check1){
                         Intent InboxPage = new Intent(HomeActivity.this, InboxActivity.class);
                         startActivity(InboxPage); //Redirect to list of all rooms
                     }
                     else{
                         Toast.makeText(HomeActivity.this, "You can not access this page as you have not Checked-In", Toast.LENGTH_SHORT).show();
                     }
                 }
             }
        );

        keyBtn = (Button) findViewById(R.id.keyCardBtn);
        keyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(g.getCheckInDate().equals("") || g.getCheckInDate() == null
                        || g.getCheckOutDate().equals("") || g.getCheckOutDate() == null) //Means no reservation made
                {
                    Toast.makeText(HomeActivity.this, "You have no reservations",Toast.LENGTH_SHORT).show();
                }
                else //If there is reservation, setup intent and send checkIn and checkOut date to new activity
                {
                    Intent keyPage = new Intent(HomeActivity.this, KeyCardActivity.class);

                    Bundle b = new Bundle();
                    b.putBoolean("checkedIn", g.isCheckedIn());
                    b.putString("keycode", g.getKeyCode());
                    keyPage.putExtra("keyInfo", b);
                    //keyPage.putExtra("checkedIn", g.isCheckedIn());
                    startActivity(keyPage);
                }



                Intent keyPage = new Intent(HomeActivity.this, KeyCardActivity.class);


                //b.putBoolean("checkedIn", g.isCheckedIn());
                //b.putString("keyCode", g.getKeyCode());
                //keyPage.putExtras(b);
               // keyPage.putExtra("checkedIn", g.isCheckedIn());
                //keyPage.putExtra("keyCode",  g.getKeyCode());
                //startActivity(keyPage);
            }
        });
        feedbackBtn = (Button)findViewById(R.id.feedbackBtn);
        String userID = user.getUid();

        myRef.child("Guest").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
                final Boolean check = g.isCheckedIn();

                feedbackBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (check){
                            Intent feedback = new Intent(HomeActivity.this, FeedbackActivity.class);
                            startActivity(feedback); //Redirect to feedback page
                        }
                        else{
                            Toast.makeText(HomeActivity.this,"You don't have access to Feedback since you are not checked-in.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        checkInBtn = (Button)findViewById(R.id.checkInBtn);
        checkInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Log.d(TAG, "onClick: " + g.getCheckInDate());

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
            g.setKeyCode(dataSnapshot.getValue(Guest.class).getKeyCode());

    }

    public void logout(){
        Toast.makeText(HomeActivity.this,"Logged Out",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
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
    private void showData(DataSnapshot dataSnapshot) {
        g.setCheckedIn(dataSnapshot.getValue(Guest.class).isCheckedIn());
    }

    private void updateDataForToday() //Method to check current date and update room and guest information
    {
        myRef.child("Rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateRoomInfo(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updateGuestInformation();


    }

    private void updateGuestInformation()
    {
        if(g.getCheckOutDate().equals("") || g.getCheckOutDate() ==  null)
        {
            return; //No checkout date set for guest. Means they aren't checked in
        }
        StringTokenizer st0 = new StringTokenizer(g.getCheckOutDate(),"-");

        if(st0.hasMoreTokens())
        {
            year2Tok = Integer.parseInt(st0.nextToken());
            month2Tok = Integer.parseInt(st0.nextToken());
            day2Tok = Integer.parseInt(st0.nextToken());
            outDateTok1 = parseDate(year2Tok, month2Tok, day2Tok);
        }

        if(currentDate.compareTo(outDateTok1) > 0) //If currentDate is past checkout date of guest
        {
            g.setCheckedIn(false);
            g.setCheckInDate("");
            g.setCheckOutDate("");
            g.setRoomNum("");

            Map<String,Object> guestUpdate = new HashMap<>();
            guestUpdate.put("checkOutDate", "");
            guestUpdate.put("checkInDate", "");
            guestUpdate.put("roomNum", "");
            guestUpdate.put("checkedIn", false);



            myRef.child("Guest").child(userID).updateChildren(guestUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(HomeActivity.this, "Guest Info updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                        finish();

                    }else{
                        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                        finish();
                    }
                }
            });
        }
    }
    private void updateRoomInfo(DataSnapshot dataSnapshot) {

        for (DataSnapshot data : dataSnapshot.getChildren()) {

            //Log.d(TAG, "showData: " + data.getValue(Room.class).getRoomType());

            Room room = new Room(); // create new object

            room.setRoomId(data.getValue(Room.class).getRoomId());
            room.setRoomType(data.getValue(Room.class).getRoomType());
            room.setIsAvailable(data.getValue(Room.class).getIsAvailable());
            room.setCheckInDate(data.getValue(Room.class).getCheckInDate());
            room.setCheckOutDate(data.getValue(Room.class).getCheckOutDate());

            //Create LocalDates objects of the dates for comparison
            StringTokenizer st0 = new StringTokenizer(room.getCheckInDate(),"-");

            if(st0.hasMoreTokens())
            {
                year0Tok = Integer.parseInt(st0.nextToken());
                month0Tok = Integer.parseInt(st0.nextToken());
                day0Tok = Integer.parseInt(st0.nextToken());
                inDateTok = parseDate(year0Tok, month0Tok, day0Tok);
            }

            StringTokenizer st1 = new StringTokenizer(room.getCheckOutDate(),"-");

            if(st1.hasMoreTokens())
            {
                year1Tok = Integer.parseInt(st1.nextToken());
                month1Tok = Integer.parseInt(st1.nextToken());
                day1Tok = Integer.parseInt(st1.nextToken());
                outDateTok = parseDate(year1Tok, month1Tok, day1Tok);
            }

            if(outDateTok != null && currentDate.compareTo(outDateTok) > 0) //If current date is past check out date
            {
                //Update room object information and update in database
                room.setCheckOutDate("");
                room.setCheckInDate("");
                room.setIsAvailable(true);

                if(g.getRoomNum().equals("") || g.getRoomNum() == null)
                {
                    return; //Means the guest is not assigned to a room
                }

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
                roomList.put("checkOutDate", "");
                roomList.put("checkInDate", "");
                roomList.put("isAvailable", true);
                roomList.put("checkedIn", false);

                myRef.child("Room").child(temp).updateChildren(roomList).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(HomeActivity.this, "Room Info updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                            finish();

                        }else{
                            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                            finish();
                        }
                    }
                });
            }

        }
    }

    private LocalDate parseDate(int year, int month, int date)
    {
        return LocalDate.of(year, month, date);
    }


}
