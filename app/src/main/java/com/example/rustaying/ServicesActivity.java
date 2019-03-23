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

    private Spinner button2; //never used

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


        //This code looks wrong / might be in the wrong activity - Mo
        //I copied your code and deleted all branches bc too many errors were happening

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.button2); // need to create spinner not button
        //create a list of items for the spinner.
        String[] items = new String[]{"1","2","three"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
    //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
    //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }
}
