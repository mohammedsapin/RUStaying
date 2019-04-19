package com.example.rustaying;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CheckInActivity extends AppCompatActivity {

    private static final String TAG = "CheckInActivity";
    private TextView inDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        //Get data from HomeActivity
        Intent i = getIntent();
        Bundle b = i.getBundleExtra("reservationDates");
        String in = b.getString("checkInDate");

        inDate = (TextView) findViewById(R.id.inDate);
        inDate.setText(in);


    }
}