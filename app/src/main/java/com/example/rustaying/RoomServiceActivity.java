/************************
 Authors:
 Eric Zhang
 Thomas Tran
 Rameen Masood
 *************************/

package com.example.rustaying;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Random;

public class RoomServiceActivity extends AppCompatActivity {

    Service roomservice = new Service();
    DatePickerDialog dateDialog;
    Button dateBtn1;
    Button viewBtn;
    TextView date1;
    Calendar c;
    LocalDate requestDate;

    private Button back;
    private Button submitButton;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    String userID;


    private EditText answerBox1;
    CheckBox checkbox1;
    CheckBox checkbox2;
    CheckBox checkbox3;
    CheckBox checkbox4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_service);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_account:
                        Intent account = new Intent(RoomServiceActivity.this,ProfileActivity.class);
                        startActivity(account);
                        break;
                    case R.id.navigation_home:
                        Intent home = new Intent(RoomServiceActivity.this,HomeActivity.class);
                        startActivity(home);
                        break;
                    case R.id.navigation_services:
                        Intent services = new Intent(RoomServiceActivity.this,
                                ServicesActivity.class);
                        startActivity(services);
                        break;
                }
                return false;
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        back = (Button) findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ba = new Intent(RoomServiceActivity.this, ServicesActivity.class);
                startActivity(ba);
            }
        });


        dateBtn1 = (Button) findViewById(R.id.calendarBtn1);
        viewBtn = (Button) findViewById(R.id.viewRoomsBtn);

        date1 = (TextView) findViewById(R.id.requestDateR);


        dateBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                final LocalDate currentDate = parseDate(year, (month + 1), day);
                dateDialog = new DatePickerDialog(RoomServiceActivity.this, R.style.Theme_AppCompat, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth1) {

                        requestDate = parseDate(year1, (month1 + 1), dayOfMonth1);
                        String requestDate1=requestDate.toString();
                        roomservice.setRequestDate(requestDate1);

                        if(requestDate.compareTo(currentDate) < 0)
                        {
                            Toast.makeText(RoomServiceActivity.this, "Invalid Date",
                                    Toast.LENGTH_SHORT).show();
                            requestDate = null;
                        }
                        else
                        {
                            date1.setText((month1 + 1) + "/" + dayOfMonth1 + "/" + year1);
                        }

                    }
                }, year, month, day);
                dateDialog.show();
            }
        });

        Spinner hours = (Spinner) findViewById(R.id.hours);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.hours));
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hours.setAdapter(adapter1);
        //  String hourValue, minuteValue, ampmValue, numb
        hours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String hourValue = parent.getItemAtPosition(position).toString();
                roomservice.setHourValue(hourValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Spinner minutes = (Spinner) findViewById(R.id.minutes);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.minutes));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minutes.setAdapter(adapter2);
        minutes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String minuteValue = parent.getItemAtPosition(position).toString();
                roomservice.setMinuteValue(minuteValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner ampm = (Spinner) findViewById(R.id.ampm);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.ampm));
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ampm.setAdapter(adapter3);
        // String ampmValue=" ";
        ampm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ampmValue = parent.getItemAtPosition(position).toString();
                roomservice.setAmpmValue(ampmValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //checkbox listener to see which boxes have been checked
        checkbox1=(CheckBox)findViewById(R.id.checkBox1);
        checkbox2=(CheckBox)findViewById(R.id.checkBox2);
        checkbox3=(CheckBox)findViewById(R.id.checkBox3);
        checkbox4=(CheckBox)findViewById(R.id.checkBox4);

        checkbox1.setOnClickListener(new View.OnClickListener(){
                                         @Override
                                         public void onClick(View v){
                                             if (checkbox1.isChecked()){
                                                 String towels = checkbox1.getText().toString();
                                                 roomservice.setTowels(towels);
                                             }
                                         }
                                     }
        );
        checkbox2.setOnClickListener(new View.OnClickListener(){
                                         @Override
                                         public void onClick(View v){
                                             if (checkbox2.isChecked()){
                                                 String soap = checkbox2.getText().toString();
                                                 roomservice.setSoap(soap);
                                             }
                                         }
                                     }
        );
        checkbox3.setOnClickListener(new View.OnClickListener(){
                                         @Override
                                         public void onClick(View v){
                                             if (checkbox3.isChecked()){
                                                 String bedsheets = checkbox3.getText().toString();
                                                 roomservice.setBedsheets(bedsheets);
                                             }
                                         }
                                     }
        );
        checkbox4.setOnClickListener(new View.OnClickListener(){
                                         @Override
                                         public void onClick(View v){
                                             if (checkbox4.isChecked()){
                                                 String cleaningservice = checkbox4.getText().toString();
                                                 roomservice.setCleaningservice(cleaningservice);
                                             }
                                         }
                                     }
        );

        //create new request id by finding previous request in database and incrementing by 1
        FirebaseDatabase.getInstance().getReference().child("Service")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        long max=0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot snapshot2 : snapshot.getChildren()){
                                if (snapshot2.child("id").getValue()!=null) {
                                    long id = Integer.parseInt(snapshot2.child("id").getValue().toString());
                                    if (id>max){
                                        max=id;
                                    }
                                }
                            }
                        }
                        max++;
                        roomservice.setId(max);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        //on submission, retrieve all values set in the empty service object, and pass into a new object
        //then pass into database
        answerBox1 = (EditText) findViewById(R.id.A1);
        submitButton= (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                long id1 = roomservice.getId();
                Random rand = new Random();
                long random = 100000000 + rand.nextInt(900000000);
                roomservice.setRequestID(random);
                long requestID=roomservice.getRequestID();
                String request= Long.toString(requestID);
                String hourValue = roomservice.getHourValue();
                String minuteValue = roomservice.getMinuteValue();
                String ampmValue = roomservice.getAmpmValue();
                String requestedTimeRoomService = hourValue + ":" + minuteValue + " " + ampmValue;
                String requestType = "RoomService";
                String status = "Incomplete";
                String requestDate=roomservice.getRequestDate();
                String towels=roomservice.getTowels();
                String soap=roomservice.getSoap();
                String bedsheets=roomservice.getBedsheets();
                String cleaningservice=roomservice.getCleaningservice();
                final String answer1 = answerBox1.getText().toString().trim();

                String checkboxes="";
                if (towels!=null){
                    checkboxes+=" " + towels;
                }
                if(soap!=null){
                    checkboxes+=" " +soap;
                }
                if(bedsheets!=null){
                    checkboxes+=" "+bedsheets;
                }
                if(cleaningservice!=null){
                    checkboxes+=" "+cleaningservice;
                }


                if (checkboxes!=""||!TextUtils.isEmpty(answer1)) {

                    Service valettravel = new Service(requestType, requestDate,
                            requestedTimeRoomService, answer1, towels, soap, bedsheets,
                            cleaningservice, checkboxes,status, id1);

                    myRef.child("Service").child(userID).child(request).setValue(valettravel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(RoomServiceActivity.this, "Request Sent!", Toast.LENGTH_SHORT).show();
                            Intent submit = new Intent(RoomServiceActivity.this, ServicesActivity.class);
                            startActivity(submit); //Redirect to main page
                            finish();
                        }
                    });
                }

                else{
                    Toast.makeText(RoomServiceActivity.this,"Please fill out the required fields",Toast.LENGTH_SHORT).show();
                }




            }
        });




    }
    private LocalDate parseDate(int year, int month, int date)
    {
        return LocalDate.of(year, month, date);
    }
}