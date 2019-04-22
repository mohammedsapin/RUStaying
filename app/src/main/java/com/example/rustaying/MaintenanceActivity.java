package com.example.rustaying;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import android.app.DatePickerDialog;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Random;



public class MaintenanceActivity extends AppCompatActivity {
    Service maintenance = new Service();

    private static final String TAG = "MaintenaceActivity";
    private Button back;
    private Button submitButton;

    DatePickerDialog dateDialog;

    Button dateBtn1;
    Button viewBtn;
    TextView date1;
    Calendar c;

    private EditText answerBox1;

    LocalDate requestDate; //LocalDate object used in ResInfo object


    //
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    String userID;
    CheckBox checkbox1;
    CheckBox checkbox2;
    CheckBox checkbox3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_account:
                        Intent account = new Intent(MaintenanceActivity.this,ProfileActivity.class);
                        startActivity(account);
                        break;
                    case R.id.navigation_home:
                        break;
                    case R.id.navigation_services:
                        Intent services = new Intent(MaintenanceActivity.this,
                                ServicesActivity.class);
                        startActivity(services);
                        break;
                }
                return false;
            }
        });

        back = (Button) findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ba = new Intent(MaintenanceActivity.this, ServicesActivity.class);
                startActivity(ba);
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
                maintenance.setHourValue(hourValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                dateDialog = new DatePickerDialog(MaintenanceActivity.this, R.style.Theme_AppCompat, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth1) {
                        requestDate = parseDate(year1, (month1 + 1), dayOfMonth1);
                        String requestDate1=requestDate.toString();
                        maintenance.setRequestDate(requestDate1);

                        if(requestDate.compareTo(currentDate) < 0)
                        {
                            Toast.makeText(MaintenanceActivity.this, "Invalid Date",
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




        Spinner minutes = (Spinner) findViewById(R.id.minutes);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.minutes));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minutes.setAdapter(adapter2);
        minutes.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String minuteValue = parent.getItemAtPosition(position).toString();
                maintenance.setMinuteValue(minuteValue);
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
                maintenance.setAmpmValue(ampmValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        checkbox1=(CheckBox)findViewById(R.id.checkBox1);
        checkbox2=(CheckBox)findViewById(R.id.checkBox2);
        checkbox3=(CheckBox)findViewById(R.id.checkBox3);

        checkbox1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (checkbox1.isChecked()){
                    String bathroom = checkbox1.getText().toString();
                    maintenance.setBathroom(bathroom);

                }
            }
        }
        );
        checkbox2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (checkbox2.isChecked()){
                    String electronic = checkbox2.getText().toString();
                    maintenance.setElectronic(electronic);
                }
            }
        }
        );
        checkbox3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (checkbox3.isChecked()){
                    String lighting = checkbox3.getText().toString();
                    maintenance.setLighting(lighting);
                }
            }
        }
        );


        answerBox1 = (EditText) findViewById(R.id.A1);
        submitButton= (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Random rand = new Random();
                long random = 100000000 + rand.nextInt(900000000);
                maintenance.setRequestID(random);
                long requestID=maintenance.getRequestID();
                String request= Long.toString(requestID);

                final String answer1 = answerBox1.getText().toString().trim();
                String hourValue = maintenance.getHourValue();
                String minuteValue = maintenance.getMinuteValue();
                String ampmValue = maintenance.getAmpmValue();
                String requestedTimeMaintenance = hourValue + ":" + minuteValue + " " + ampmValue;
                String requestType = "Maintenance";
                String requestDate = maintenance.getRequestDate();
                String bathroom=maintenance.getBathroom();
                String electronic=maintenance.getElectronic();
                String lighting=maintenance.getLighting();

                String checkboxes="";
                if (bathroom!=null){
                    checkboxes+=" " + bathroom;
                }
                if(electronic!=null){
                    checkboxes+=" " +electronic;
                }
                if(lighting!=null){
                    checkboxes+=" "+lighting;
                }
                Service service= new Service(requestType, requestDate, requestedTimeMaintenance, answer1, bathroom, electronic, lighting, checkboxes);
                myRef.child("Service").child(userID).child(request).setValue(service).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MaintenanceActivity.this, "Request Sent!",Toast.LENGTH_SHORT).show();
                        Intent submit = new Intent(MaintenanceActivity.this,ServicesActivity.class);
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
