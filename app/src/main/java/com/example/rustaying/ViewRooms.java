/************************
 Authors:
 Shilp Shah
 Mathew Varghese
 *************************/

package com.example.rustaying;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewRooms extends AppCompatActivity {

    private static final String TAG = "ViewRooms";

    private ArrayList<Room> roomList = new ArrayList<>();

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rooms);

        //GET DATA from ReservationActivity
        Intent i = getIntent();
        Bundle b = i.getBundleExtra("resInfo");

        //String checkIn = b.getString("checkIn");
        //String[] roomTypes = new String[3];
        //roomTypes = b.getStringArray("roomTypes");

        //Log.d(TAG, checkIn);
        //Log.d(TAG, roomTypes[0]);

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

    private void createRecycleView(){
        Log.d(TAG, "createRecycleView: Started view");
        RecyclerView recyclerView = findViewById(R.id.viewRoomsRecycleView);
        ViewRoomsAdapter adapter = new ViewRoomsAdapter(this,roomList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()){

            Room room = new Room(); // create new object

            room.setRoomId(data.getValue(Room.class).getRoomId()); // set first name
            room.setRoomType(data.getValue(Room.class).getRoomType()); // set last name
            room.setIsAvailable(data.getValue(Room.class).getIsAvailable()); // set email

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