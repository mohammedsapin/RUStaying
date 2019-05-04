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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.time.LocalDate;
import java.util.Calendar;
import java.util.Random;

public class ValetTravelActivity extends AppCompatActivity {

    Service valettravel = new Service();
    private static final String TAG = "ValetTravel Activity";
    private EditText answerBox1;
    private EditText answerBox2;
    private EditText answerBox3;
    private EditText answerBox4;
    private Button back;
    private Button submitButton;


    DatePickerDialog dateDialog;
    Button dateBtn1;
    Button viewBtn;
    TextView date1;
    Calendar c;
    LocalDate requestDate;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valet_travel);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_account:
                        Intent account = new Intent(ValetTravelActivity.this,ProfileActivity.class);
                        startActivity(account);
                        break;
                    case R.id.navigation_home:
                        Intent home = new Intent(ValetTravelActivity.this,HomeActivity.class);
                        startActivity(home);
                        break;
                    case R.id.navigation_services:
                        Intent services = new Intent(ValetTravelActivity.this,ServicesActivity.class);
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
                Intent ba = new Intent(ValetTravelActivity.this, ServicesActivity.class);
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
                dateDialog = new DatePickerDialog(ValetTravelActivity.this, R.style.Theme_AppCompat, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth1) {

                        requestDate = parseDate(year1, (month1 + 1), dayOfMonth1);
                        String requestDate1=requestDate.toString();
                        valettravel.setRequestDate(requestDate1);

                        if(requestDate.compareTo(currentDate) < 0)
                        {
                            Toast.makeText(ValetTravelActivity.this, "Invalid Date",
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
                valettravel.setHourValue(hourValue);
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
                valettravel.setMinuteValue(minuteValue);
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
                valettravel.setAmpmValue(ampmValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner numberTraveling = (Spinner) findViewById(R.id.numberTravelingSpinner);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.numberTravelingSpinner));
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberTraveling.setAdapter(adapter4);

        numberTraveling.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String numberTraveling1 = parent.getItemAtPosition(position).toString();
                Integer numberTraveling2=Integer.valueOf(numberTraveling1);
                valettravel.setNumberTraveling(numberTraveling2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // String numberTraveling1="-1";
                // valettravel.setNumberTraveling(numberTraveling1);

            }
        });


        answerBox1 = (EditText) findViewById(R.id.A1);
        answerBox2 = (EditText) findViewById(R.id.A2);
        answerBox3 = (EditText) findViewById(R.id.A3);
        answerBox4 = (EditText) findViewById(R.id.A4);




        submitButton= (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Random rand = new Random();

                long random = 100000000 + rand.nextInt(900000000);
                valettravel.setRequestID(random);
                long requestID=valettravel.getRequestID();
                String request= Long.toString(requestID);

                final String startingStreet = answerBox1.getText().toString().trim();
                final String  destinationStreet= answerBox2.getText().toString().trim();

                final String startingCityStateZip = answerBox3.getText().toString().trim();
                final String destinationCityStateZip = answerBox4.getText().toString().trim();

                String hourValue = valettravel.getHourValue();
                String minuteValue = valettravel.getMinuteValue();
                String ampmValue = valettravel.getAmpmValue();
                String requestedTimeValet = hourValue + ":" + minuteValue + " " + ampmValue;
                String requestType = "ValetTravel";
                String requestDate=valettravel.getRequestDate();
                long numberTraveling1=valettravel.getNumberTraveling();

                if (!TextUtils.isEmpty(startingStreet) && !TextUtils.isEmpty(startingCityStateZip)&& !TextUtils.isEmpty(destinationCityStateZip)&& !TextUtils.isEmpty(destinationStreet)) {
                    Service valettravel = new Service(requestType,requestedTimeValet, requestDate, startingStreet,
                            destinationStreet, startingCityStateZip, destinationCityStateZip, numberTraveling1);

                    myRef.child("Service").child(userID).child(request).setValue(valettravel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ValetTravelActivity.this, "Request Sent!",Toast.LENGTH_SHORT).show();
                            Intent submit = new Intent(ValetTravelActivity.this,ServicesActivity.class);
                            startActivity(submit); //Redirect to main page
                            finish();
                        }
                    });

                }

                else{
                    Toast.makeText(ValetTravelActivity.this,"Please fill out the required fields",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private LocalDate parseDate(int year, int month, int date)
    {
        return LocalDate.of(year, month, date);
    }
}