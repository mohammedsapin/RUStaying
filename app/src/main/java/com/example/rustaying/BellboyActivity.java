package com.example.rustaying;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BellboyActivity extends AppCompatActivity{ //implements OnItemSelectedListener{

    Service bellboy = new Service();

    private static final String TAG = "BellboyActivity";
    private Button back;
    private Button submitButton;

    //
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bellboy);

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
                int luggage = Integer.parseInt(luggageValue);
                bellboy.setLuggageValue(luggage);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submitButton= (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String hourValue = bellboy.getHourValue();
                String minuteValue = bellboy.getMinuteValue();
                String ampmValue = bellboy.getAmpmValue();
                Integer numLuggageValue = bellboy.getLuggageValue();
                String requestedTime = hourValue + ":" + minuteValue + " " + ampmValue;
                String requestType = "Bellboy";
                Service service = new Service(requestType,numLuggageValue,requestedTime);
                myRef.child("Service").child(userID).setValue(service).addOnCompleteListener(new OnCompleteListener<Void>() {
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
}