package com.example.rustaying;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
//Author: Keya Patel| Assisted: Keya Patel| Debugged: Keya Patel
public class PaymentActivity extends AppCompatActivity {
    private static final String TAG = "Payment Activity";
    private EditText answerBox1, answerBox2, answerBox3, answerBox4, answerBox5, answerBox6;
    private Spinner  exMonth,exYear;
    private Button paymentButton;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private String userID;
    private String[] arraySpinnerMonth;
    private String[] arraySpinnerYear=new String[50];

    private Guest g = new Guest();
    private String checkIn, checkOut, roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_account:
                        Intent account = new Intent(PaymentActivity.this,ProfileActivity.class);
                        startActivity(account);
                        break;
                    case R.id.navigation_home:
                        Intent home = new Intent(PaymentActivity.this,HomeActivity.class);
                        startActivity(home);
                        break;
                    case R.id.navigation_services:
                        break;
                }
                return false;
            }
        });
        Spinner spinnerX1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerX1.setAdapter(adapter1);

        Spinner spinnerX2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.years_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerX2.setAdapter(adapter2);


        //Get resInfo data from newRoomAdapater Activity
        Intent i = this.getIntent();
        Bundle b = i.getBundleExtra("resInfo");
        checkIn = b.getString("checkInDate");
        checkOut = b.getString("checkOutDate");
        roomId = b.getString("roomId");


        answerBox1 = (EditText) findViewById(R.id.nameCard);
        answerBox2 = (EditText) findViewById(R.id.numCard);
        answerBox3 = (EditText) findViewById(R.id.ccvCard);
        answerBox4 = (EditText) findViewById(R.id.billingStreetAdd);
        answerBox5 = (EditText) findViewById(R.id.cityAdd);
        answerBox6 = (EditText) findViewById(R.id.zipAdd);
        exMonth = findViewById(R.id.spinner1);
        exYear = findViewById(R.id.spinner2);

        paymentButton = (Button) findViewById(R.id.button);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String answer1 = answerBox1.getText().toString().trim();// name
                final String answer2 = answerBox2.getText().toString().trim();//number
                final String answer3 = answerBox3.getText().toString().trim();//ccv
                final String answer4 = answerBox4.getText().toString().trim();//street
                final String answer5 = answerBox5.getText().toString().trim();//city
                final String answer6 = answerBox6.getText().toString().trim();//zip
                final String spin1 = exMonth.getSelectedItem().toString().trim();//month
                final String spin2 = exYear.getSelectedItem().toString().trim();//date
                final String spin = spin1+"/"+spin2;

                if (!TextUtils.isEmpty(answer1) && !TextUtils.isEmpty(answer2) && !TextUtils.isEmpty(answer3)
                    && !TextUtils.isEmpty(answer4) && !TextUtils.isEmpty(answer5) && !TextUtils.isEmpty(answer6)
                        && !TextUtils.isEmpty(spin1) && !TextUtils.isEmpty(spin2)){

                    Map<String,Object> list = new HashMap<>();
                    list.put("nameOnCCard", answer1);
                    list.put("creditCardNumber", answer2);
                    list.put("CCV", answer3);
                    list.put("billingAddress", answer4 + "," + answer5 + "," +answer6);
                    list.put("expirationDate", spin);

                    list.put("validPayment", true);

                    myRef.child("Guest").child(userID).updateChildren(list).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                updateInformation(roomId);
                                Toast.makeText(PaymentActivity.this, "Payment Confirmed", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(PaymentActivity.this, HomeActivity.class));
                                finish();
                            }
                            else{
                                Toast.makeText(PaymentActivity.this,"Please fill out the required fields",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(PaymentActivity.this,"Please fill out the required fields",Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                //inDates = dataSnapshot.getValue(Room.class).getCheckInDate();
                //outDates = dataSnapshot.getValue(Room.class).getCheckOutDate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Map<String,Object> roomList = new HashMap<>();
        roomList.put("checkInDate", checkIn);
        roomList.put("checkOutDate", checkOut);
        roomList.put("checkedIn", false);
        roomList.put("isAvailable", false);


        myRef.child("Rooms").child(temp).updateChildren(roomList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Toast.makeText(PaymentActivity.this, "Room info updated", Toast.LENGTH_SHORT).show();
                    //Intent homeActivity = new Intent(mCtx, HomeActivity.class);
                    //startActivity(homeActivity);

                    //mCtx.startActivity(homeActivity);

                }else{
                    Toast.makeText(PaymentActivity.this, "Error updating info", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(EditInfoActivity.this, ProfileActivity.class));
                    //finish();
                }
            }
        });

        Map<String,Object> list = new HashMap<>();
        list.put("checkInDate", checkIn);
        list.put("checkOutDate", checkOut);
        list.put("checkedIn", false);
        list.put("reservationMade", true);
        list.put("roomNum", roomId);

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
}