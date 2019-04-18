package com.example.rustaying;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private Button registerBtn;
    private Button loginBtn;
    private Button hotelInfoBtn;
    private Button viewRoomsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = (Button)findViewById(R.id.loginBtn1);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginPage = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginPage);
            }
        });

        registerBtn = (Button)findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerPage = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerPage);
            }
        });


        viewRoomsBtn = (Button)findViewById(R.id.viewRoomsBtn);
        viewRoomsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewRooms = new Intent(MainActivity.this, ReservationActivity.class);
                startActivity(viewRooms);
            }
        });


        hotelInfoBtn = (Button)findViewById(R.id.hotelInfoBtn);
        hotelInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hotelInfoPage = new Intent(MainActivity.this, HotelInfo.class);
                startActivity(hotelInfoPage);
            }
        });

    }
}

