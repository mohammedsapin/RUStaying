package com.example.rustaying;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ServicesActivity extends AppCompatActivity {

    private static final String TAG = "ServicesActivity";

    Button bellboy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_account:
                        Intent account = new Intent(ServicesActivity.this,ProfileActivity.class);
                        startActivity(account);
                        break;
                    case R.id.navigation_home:
                        Intent home = new Intent(ServicesActivity.this,HomeActivity.class);
                        startActivity(home);
                        break;
                    case R.id.navigation_services:
                        break;
                }
                return false;
            }
        });
        bellboy = (Button) findViewById(R.id.bellboybtn);
        bellboy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bu = new Intent(ServicesActivity.this, BellboyActivity.class);
                startActivity(bu);
            }
        });




    }
}