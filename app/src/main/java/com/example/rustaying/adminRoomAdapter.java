/************************
 Authors:
 Shilp Shah
 Mathew Varghese
 *************************/

package com.example.rustaying;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adminRoomAdapter extends RecyclerView.Adapter<adminRoomAdapter.RoomViewHolder>{

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
    boolean temp = false;


    public adminRoomAdapter()
    {

    }

    //Constructor to create new adminRoomAdapter
    public adminRoomAdapter(Context mCtx, ArrayList<Room> roomList, ResInfo resInfo) {
        this.mCtx = mCtx;
        this.roomList = roomList;
        this.resInfo = resInfo;
    }


    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        /*
        mAuth = FirebaseAuth.getInstance(); //mAuth
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference(); //dbRef
        user = mAuth.getCurrentUser();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user != null){
                    temp = true;
                    userID = user.getUid();
                    Log.d(TAG, "onAuthStateChanged: Signed In");
                }else{
                    temp = false;
                    Log.d(TAG, "onAuthStateChanged: Signed out");
                }
            }
        };
        */

        //LayoutInflater inflater = LayoutInflater.from(mCtx);
        //View view = inflater.inflate(R.layout.new_rooms, null);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_rooms, viewGroup, false);
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

        //PRICING ALGORTHIM
        if(room.getRoomType().equals("Single"))
        {
            int pr = 250;

            //check for peak months
            if(resInfo.getCheckIn().getMonth()== Month.DECEMBER //Christmas
                    || resInfo.getCheckIn().getMonth()== Month.MARCH //Spring Break
                    || resInfo.getCheckIn().getMonth()== Month.JUNE //summer
                    || resInfo.getCheckIn().getMonth()== Month.JULY //summer
                    || resInfo.getCheckIn().getMonth()== Month.AUGUST //summer
            )
            {
                pr+=150;
            }
            //check if weekend
            if(resInfo.getCheckIn().getDayOfWeek()== DayOfWeek.FRIDAY
                    || resInfo.getCheckIn().getDayOfWeek()== DayOfWeek.SATURDAY)
            {
                pr+=100;
            }

            String pri = "$" + pr;
            roomViewHolder.price.setText(pri);
            myImage = ResourcesCompat.getDrawable(res, R.drawable.singleroom, null);
        }
        else if(room.getRoomType().equals("Double"))
        {
            int pr = 300;

            //check for peak months
            if(resInfo.getCheckIn().getMonth()== Month.DECEMBER //Christmas
                    || resInfo.getCheckIn().getMonth()== Month.MARCH //Spring Break
                    || resInfo.getCheckIn().getMonth()== Month.JUNE //summer
                    || resInfo.getCheckIn().getMonth()== Month.JULY //summer
                    || resInfo.getCheckIn().getMonth()== Month.AUGUST //summer
            )
            {
                pr+=150;
            }

            //check if weekend
            if(resInfo.getCheckIn().getDayOfWeek()==DayOfWeek.FRIDAY
                    || resInfo.getCheckIn().getDayOfWeek()==DayOfWeek.SATURDAY)
            {
                pr+=100;
            }

            String pri = "$" + pr;
            roomViewHolder.price.setText(pri);
            myImage = ResourcesCompat.getDrawable(res, R.drawable.doubleroom, null);
        }
        else if(room.getRoomType().equals("Queen"))
        {
            int pr = 350;

            //check for peak months
            if(resInfo.getCheckIn().getMonth()== Month.DECEMBER //Christmas
                    || resInfo.getCheckIn().getMonth()== Month.MARCH //Spring Break
                    || resInfo.getCheckIn().getMonth()== Month.JUNE //summer
                    || resInfo.getCheckIn().getMonth()== Month.JULY //summer
                    || resInfo.getCheckIn().getMonth()== Month.AUGUST //summer
            )
            {
                pr+=150;
            }

            //check if weekend
            if(resInfo.getCheckIn().getDayOfWeek()==DayOfWeek.FRIDAY
                    || resInfo.getCheckIn().getDayOfWeek()==DayOfWeek.SATURDAY)
            {
                pr+=100;
            }

            String pri = "$" + pr;
            roomViewHolder.price.setText(pri);
            myImage = ResourcesCompat.getDrawable(res, R.drawable.queenroom, null);
        }
        else if(room.getRoomType().equals("King"))
        {
            int pr = 450;

            //check for peak months
            if(resInfo.getCheckIn().getMonth()== Month.DECEMBER //Christmas
                    || resInfo.getCheckIn().getMonth()== Month.MARCH //Spring Break
                    || resInfo.getCheckIn().getMonth()== Month.JUNE //summer
                    || resInfo.getCheckIn().getMonth()== Month.JULY //summer
                    || resInfo.getCheckIn().getMonth()== Month.AUGUST //summer
            )
            {
                pr+=150;
            }

            //check if weekend
            if(resInfo.getCheckIn().getDayOfWeek()==DayOfWeek.FRIDAY
                    || resInfo.getCheckIn().getDayOfWeek()==DayOfWeek.SATURDAY)
            {
                pr+=100;
            }

            String pri = "$" + pr;
            roomViewHolder.price.setText(pri);
            myImage = ResourcesCompat.getDrawable(res, R.drawable.hotelroom, null);

        }



        roomViewHolder.imageView.setImageDrawable(myImage);

        /*
        roomViewHolder.bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mCtx);
                alertDialog.setMessage("Confirm Room Booking for Room: " + roomNum + "? \n Check in date is: " +
                        resInfo.getCheckIn().toString() + "\n Check out date is: " +
                        resInfo.getCheckOut().toString())
                        //Positive button is Yes, meaning the use wants to logout
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Confirm booking
                                //Send data to database
                                updateInformation();


                                //Confirmation message
                                Toast.makeText(mCtx, "Booking Confirmed!",Toast.LENGTH_SHORT).show();

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
        */
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public void updateInformation()
    {
        Map<String,Object> list = new HashMap<>();
        list.put("checkInDate",resInfo.getCheckIn().toString());
        list.put("checkOutDate",resInfo.getCheckOut().toString());
        list.put("checkedIn", true);

        myRef.child("Guest").child(userID).updateChildren(list).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    //Toast.makeText(mCtx, "Info updated", Toast.LENGTH_SHORT).show();
                    Intent homeActivity = new Intent(mCtx, HomeActivity.class);
                    //startActivity(homeActivity);

                    mCtx.startActivity(homeActivity);

                }else{
                    //startActivity(new Intent(EditInfoActivity.this, ProfileActivity.class));
                    //finish();
                }
            }
        });    }

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