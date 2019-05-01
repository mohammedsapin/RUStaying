package com.example.rustaying;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class newRoomAdapter extends RecyclerView.Adapter<newRoomAdapter.RoomViewHolder>{

    private static final String TAG = "newRoomAdapter";
    private Context mCtx;
    private ArrayList<Room> roomList;
    private ResInfo resInfo = new ResInfo();
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private String userID;
    private String inDates, outDates;

    ArrayList roomIds = new ArrayList();
    boolean concatDates = false;

    public newRoomAdapter(Context mCtx, ArrayList<Room> roomList, ResInfo resInfo) {
        this.mCtx = mCtx;
        this.roomList = roomList;
        this.resInfo = resInfo;
    }


    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

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
                    Log.d(TAG, "onAuthStateChanged: Signed In");
                }else{

                    Log.d(TAG, "onAuthStateChanged: Signed out");
                }
            }
        };

        
        //LayoutInflater inflater = LayoutInflater.from(mCtx);
        //View view = inflater.inflate(R.layout.new_rooms, null);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_rooms, viewGroup, false);
        //View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);

        RoomViewHolder holder = new RoomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder roomViewHolder, int i) {

        final Room room = roomList.get(i);
        Log.i(TAG, room.getRoomType());

        roomViewHolder.roomType.setText(room.getRoomType());
        roomViewHolder.roomNum.setText(room.getRoomId());

        Resources res = mCtx.getResources();
        Drawable myImage = ResourcesCompat.getDrawable(res, R.drawable.singleroom, null);

        if(room.getRoomType().equals("Single"))
        {
            roomViewHolder.price.setText("$250");
            myImage = ResourcesCompat.getDrawable(res, R.drawable.singleroom, null);
        }
        else if(room.getRoomType().equals("Double"))
        {
            roomViewHolder.price.setText("$300");
            myImage = ResourcesCompat.getDrawable(res, R.drawable.doubleroom, null);
        }
        else if(room.getRoomType().equals("Queen"))
        {
            roomViewHolder.price.setText("$350");
            myImage = ResourcesCompat.getDrawable(res, R.drawable.queenroom, null);
        }
        else if(room.getRoomType().equals("King"))
        {
            roomViewHolder.price.setText("$400");
            myImage = ResourcesCompat.getDrawable(res, R.drawable.hotelroom, null);

        }

        roomViewHolder.imageView.setImageDrawable(myImage);

            roomViewHolder.bookBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mCtx);
                    alertDialog.setMessage("Confirm Room Booking for Room: " + room.getRoomId() + "? \n Check in date is: " +
                            resInfo.getCheckIn().toString() + "\n Check out date is: " +
                            resInfo.getCheckOut().toString())
                            //Positive button is Yes, meaning the use wants to logout
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Confirm booking
                                    //Send data to database
                                    updateInformation(room.getRoomId());

                                    //Confirmation message
                                    //Toast.makeText(mCtx, "Booking Confirmed!",Toast.LENGTH_SHORT).show();
                                    Intent payment = new Intent(mCtx, PaymentActivity.class);
                                    mCtx.startActivity(payment);

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
                    alert.setTitle("Confirm Reservation");
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

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public void updateInformation(String roomNum)
    {
        //Create string of room number (ex: Room 01 or Room 12)
        String temp;
        int roomInt = Integer.parseInt(roomNum);
        if(roomInt <= 9)
        {
            temp = "Room 0" + roomNum;
        }
        else
        {
            temp = "Room " + roomNum;
        }

        //Get the current dates of reservation for the room
        myRef.child("Rooms").child(temp).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                inDates = dataSnapshot.getValue(Room.class).getCheckInDate();
                outDates = dataSnapshot.getValue(Room.class).getCheckOutDate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(concatDates) //A reservation exists, concatenate to the end of it
        {
            //String concatenatedInDates = inDates + ", " + resInfo.getCheckIn().toString();
            //String concatenatedOutDates = inDates + ", " + resInfo.getCheckIn().toString();

            //inDates = inDates + " rrrr";
            //inDates += ", " + resInfo.getCheckIn().toString();
            //outDates += ", " + resInfo.getCheckOut().toString();
        }

        //Log.d(TAG, "onDataChange: " + inDates);

        Map<String,Object> roomList = new HashMap<>();
        roomList.put("checkInDate",resInfo.getCheckIn().toString());
        roomList.put("checkOutDate",resInfo.getCheckOut().toString());
        roomList.put("checkedIn", false);
        roomList.put("isAvailable", false);


        myRef.child("Rooms").child(temp).updateChildren(roomList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Toast.makeText(mCtx, "Room info updated", Toast.LENGTH_SHORT).show();
                    //Intent homeActivity = new Intent(mCtx, HomeActivity.class);
                    //startActivity(homeActivity);

                    //mCtx.startActivity(homeActivity);

                }else{
                    Toast.makeText(mCtx, "Error updating info", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(EditInfoActivity.this, ProfileActivity.class));
                    //finish();
                }
            }
        });

        Map<String,Object> list = new HashMap<>();
        list.put("checkInDate",resInfo.getCheckIn().toString());
        list.put("checkOutDate",resInfo.getCheckOut().toString());
        list.put("checkedIn", true);

        myRef.child("Guest").child(userID).updateChildren(list).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    //Toast.makeText(mCtx, "Info updated", Toast.LENGTH_SHORT).show();
                    //Intent homeActivity = new Intent(mCtx, HomeActivity.class);
                    //startActivity(homeActivity);

                    //mCtx.startActivity(homeActivity);

                }else{
                    //startActivity(new Intent(EditInfoActivity.this, ProfileActivity.class));
                    //finish();
                }
            }
        });
    }

    class RoomViewHolder extends RecyclerView.ViewHolder
    {

        ImageView imageView;
        TextView roomType, roomNum, price;
        Button bookBtn;

        public RoomViewHolder(@NonNull final View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.roomPic);
            roomType = itemView.findViewById(R.id.roomType);
            roomNum = itemView.findViewById(R.id.roomNum);
            price = itemView.findViewById(R.id.price);
            bookBtn = itemView.findViewById(R.id.bookBtn);

        }
    }
}