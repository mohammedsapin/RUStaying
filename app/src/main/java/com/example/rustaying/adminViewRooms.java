package com.example.rustaying;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class adminViewRooms extends AppCompatActivity {

    private static final String TAG = "adminViewRooms";

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private ResInfo resInfo;


    RecyclerView recyclerView;
    newRoomAdapter adapter;

    ArrayList<Room> roomList = new ArrayList<>();
    String[] receivedRoomTypes = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_rooms);

        //GET DATA from ReservationActivity
        Intent i = getIntent();
        Bundle b = i.getBundleExtra("resInfo");


        receivedRoomTypes = b.getStringArray("roomTypes");

        int inDay = b.getInt("inDay");
        int inMonth = b.getInt("inMonth");
        int inYear = b.getInt("inYear");

        int outDay = b.getInt("outDay");
        int outMonth = b.getInt("outMonth");
        int outYear = b.getInt("outYear");

        Log.i(TAG, "onCreate: " + inDay);
        Log.i(TAG, "onCreate: " + inMonth);
        Log.i(TAG, "onCreate: " + inYear);

        Log.i(TAG, "onCreate: " + outDay);
        Log.i(TAG, "onCreate: " + outMonth);
        Log.i(TAG, "onCreate: " + outYear);

        LocalDate inDate = parseDate(inYear, inMonth, inDay);
        LocalDate outDate = parseDate(outYear, outMonth, outDay);

        resInfo = new ResInfo(inDate, outDate, receivedRoomTypes);

        createRecycleView();


        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.d(TAG, "onAuthStateChanged: Signed In");
                }else{

                    Log.d(TAG, "onAuthStateChanged: Signed out");
                }
            }
        };


        myRef.child("Rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private LocalDate parseDate(int year, int month, int date)
    {
        return LocalDate.of(year, month, date);
    }

    private void createRecycleView(){
        Log.d(TAG, "createRecycleView: Started view");
        RecyclerView recyclerView = findViewById(R.id.adminRecyclerView);
        adminRoomAdapter adapter = new adminRoomAdapter(this,roomList, resInfo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void showData(DataSnapshot dataSnapshot) {

        for (DataSnapshot data : dataSnapshot.getChildren()){

            Room room = new Room(); // create new object

            room.setRoomId(data.getValue(Room.class).getRoomId()); // set first name
            room.setRoomType(data.getValue(Room.class).getRoomType()); // set last name
            room.setIsAvailable(data.getValue(Room.class).getIsAvailable()); // set email


            if(receivedRoomTypes[0] == null && room.getRoomType().equals("Single"))
            {
                continue;
            }
            else if (receivedRoomTypes[1] == null && room.getRoomType().equals("Double"))
            {
                continue;
            }
            else if(receivedRoomTypes[2] == null && room.getRoomType().equals("Queen"))
            {
                continue;
            }
            else if(receivedRoomTypes[3] == null && room.getRoomType().equals("King"))
            {
                continue;
            }


            //add object to array list
            roomList.add(new Room(room.getRoomId(),room.getRoomType(),room.getIsAvailable()));

            //Log.d(TAG, "showData: Room ID: " + room.getRoomId());
            //Log.d(TAG, "showData: Room Type: " + room.getRoomType());
            //Log.d(TAG, "showData: Availability: " + room.getIsAvailable());
            //Log.d(TAG, "showData: Array List: " + roomList );

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