package com.example.rustaying;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Random;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class BellboyActivity extends AppCompatActivity{ //implements OnItemSelectedListener{

    Service bellboy = new Service();

    private EditText answerBox1;

    private static final String TAG = "BellboyActivity";
    private Button back;
    private Button submitButton;
  
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    String userID;

    DatePickerDialog dateDialog;
    Button dateBtn1;
    Button viewBtn;
    TextView date1;
    Calendar c;
    LocalDate requestDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bellboy);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_account:
                        Intent account = new Intent(BellboyActivity.this,ProfileActivity.class);
                        startActivity(account);
                        break;
                    case R.id.navigation_home:
                        break;
                    case R.id.navigation_services:
                        Intent services = new Intent(BellboyActivity.this,ServicesActivity.class);
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
                Intent ba = new Intent(BellboyActivity.this, ServicesActivity.class);
                startActivity(ba);
            }
        });


        dateBtn1 = (Button) findViewById(R.id.calendarBtn1);
        viewBtn = (Button) findViewById(R.id.viewRoomsBtn);

        date1 = (TextView) findViewById(R.id.requestDate);


        dateBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                final LocalDate currentDate = parseDate(year, (month + 1), day);
                dateDialog = new DatePickerDialog(BellboyActivity.this, R.style.Theme_AppCompat, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth1) {

                        requestDate = parseDate(year1, (month1 + 1), dayOfMonth1);
                        String requestDate1=requestDate.toString();
                        bellboy.setRequestDate(requestDate1);


                        if(requestDate.compareTo(currentDate) < 0)
                        {
                            Toast.makeText(BellboyActivity.this, "Invalid Date",
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
        hours.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String hourValue = parent.getItemAtPosition(position).toString();
                bellboy.setHourValue(hourValue);
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
        minutes.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String minuteValue = parent.getItemAtPosition(position).toString();
                bellboy.setMinuteValue(minuteValue);
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
        ampm.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ampmValue = parent.getItemAtPosition(position).toString();
                bellboy.setAmpmValue(ampmValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner luggageNum = (Spinner) findViewById(R.id.luggageNum);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.luggageNum));
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        luggageNum.setAdapter(adapter4);

        luggageNum.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String luggageValue = parent.getItemAtPosition(position).toString();
                bellboy.setLuggageValue(luggageValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        answerBox1 = (EditText) findViewById(R.id.A1);


        submitButton= (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Random rand = new Random();
                long random = 100000000 + rand.nextInt(900000000);
                bellboy.setRequestID(random);
                long requestID = bellboy.getRequestID();
                String request= Long.toString(requestID);
                String hourValue = bellboy.getHourValue();
                String minuteValue = bellboy.getMinuteValue();
                String ampmValue = bellboy.getAmpmValue();
                String numLuggageValue = bellboy.getLuggageValue();
                String requestedTimeBellboy = hourValue + ":" + minuteValue + " " + ampmValue;
                String requestType = "Bellboy";
                String requestDate = bellboy.getRequestDate();
                final String fromWhere = answerBox1.getText().toString().trim();
                Service service = new Service(requestType,requestDate, numLuggageValue,requestedTimeBellboy, fromWhere);
                myRef.child("Service").child(userID).child(request).setValue(service).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(BellboyActivity.this, "Request Sent!",Toast.LENGTH_SHORT).show();
                        Intent submit = new Intent(BellboyActivity.this,ServicesActivity.class);
                        startActivity(submit); //Redirect to main page
                        finish();
                    }
                });
            }
        });

    }

    private LocalDate parseDate(int year, int month, int date)
    {
        return LocalDate.of(year, month, date);
    }
}