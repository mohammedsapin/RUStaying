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
import android.widget.Toast;

import java.util.regex.Pattern;

import java.time.LocalDate;
import java.util.Calendar;

public class CheckInActivity extends AppCompatActivity {

    private static final String TAG = "CheckInActivity";
    private TextView inDate, outDate;
    private Button checkInBtn;
    Calendar c;
    LocalDate currentDate, checkInDate, checkOutDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_account:
                        Intent account = new Intent(CheckInActivity.this,ProfileActivity.class);
                        startActivity(account);
                        break;
                    case R.id.navigation_home:
                        Intent home = new Intent(CheckInActivity.this,HomeActivity.class);
                        startActivity(home);
                        break;
                    case R.id.navigation_services:
                        Intent service = new Intent(CheckInActivity.this, ServicesActivity.class);
                        startActivity(service);
                        break;
                }
                return false;
            }
        });

        //Get data from HomeActivity
        Intent i = getIntent();
        Bundle b = i.getBundleExtra("reservationDates");
        final String in = b.getString("checkInDate");
        final String out = b.getString("checkOutDate");

        inDate = (TextView) findViewById(R.id.inDate);
        outDate = (TextView) findViewById(R.id.outDate);
        checkInBtn = (Button) findViewById(R.id.guestCheckInBtn);

        inDate.setText(in);
        outDate.setText(out);

        checkInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if the check in date is today's date, then allow them to check in
                c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                currentDate = parseDate(year, (month + 1), day); //Current date of LocalDate object
                checkInDate = parseStringDate(in);

                if(checkInDate.equals(currentDate))
                {
                    //Allow them to check in
                    Toast.makeText(CheckInActivity.this, "Checked In!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(CheckInActivity.this, "You can check in on: " + in, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private LocalDate parseDate(int year, int month, int date)
    {
        return LocalDate.of(year, month, date);
    }

    private LocalDate parseStringDate(String s)
    {
        String[] output = s.split("-");
        int[] numbers = new int[output.length];

        numbers[0] = Integer.parseInt(output[0]);
        numbers[1] = Integer.parseInt(output[1]);
        numbers[2] = Integer.parseInt(output[2]);

        return LocalDate.of(numbers[0], numbers[1], numbers[2]);
    }
}