package com.example.rustaying;

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
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valet_travel);

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
                int random1 = rand.nextInt(1000);
                int random2 = rand.nextInt(1000);
                int random3 = rand.nextInt(1000);
                String random = Integer.toString(random1) + Integer.toString(random2) + Integer.toString(random3);

                final String answer1 = answerBox1.getText().toString().trim();
                final String answer2 = answerBox2.getText().toString().trim();

                final String answer3 = answerBox3.getText().toString().trim();
                final String answer4 = answerBox4.getText().toString().trim();

                String hourValue = valettravel.getHourValue();
                String minuteValue = valettravel.getMinuteValue();
                String ampmValue = valettravel.getAmpmValue();
                String requestedTime = hourValue + ":" + minuteValue + " " + ampmValue;
                String requestType = "ValetTravel";


                if (!TextUtils.isEmpty(answer1) && !TextUtils.isEmpty(answer2)) {
                    //Service valettravel = new Service(requestType, requestedTime, answer1,
                            //answer3, answer2, answer4);

                    myRef.child("Service").child(userID).child(random).setValue(valettravel).addOnCompleteListener(new OnCompleteListener<Void>() {
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
}