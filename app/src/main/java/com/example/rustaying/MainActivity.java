package com.example.rustaying;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText emInput;
    EditText passInput;
    Button loginBtn;
    TextView registerBtn;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        emInput = (EditText) findViewById(R.id.emInput);
        passInput = (EditText) findViewById(R.id.passInput);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        registerBtn = (TextView)findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerPage = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerPage);
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emInput.getText().toString().trim();
                String password = passInput.getText().toString().trim();
                String adminEmail = emInput.getText().toString();
                String adminPassword = passInput.getText().toString();
                boolean check = db.checkUser(email,password);
                if (db.isEmpty(emInput) != true && db.isEmpty(passInput) != true) {
                    if (check == true) {
                        //Call database function to get a new Guest object here!!!
                        //Guest g = db.getGuestInfo(email);
                        Toast.makeText(MainActivity.this, "Welcome ", Toast.LENGTH_SHORT).show();
                        Intent homeScreen = new Intent(MainActivity.this, HomeActivity.class);
                        boolean adminCheck = db.adminCheck(adminEmail, adminPassword);
                        if (db.isEmpty(emInput) != true && db.isEmpty(passInput) != true) {
                            if (check == true) {
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                //Intent homeScreen = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(homeScreen);
                                finish();
                            } else if (adminCheck) {
                                Toast.makeText(MainActivity.this, "Admin login Successful", Toast.LENGTH_SHORT).show();
                                Intent adminScreen = new Intent(MainActivity.this, AdminActivity.class);
                                startActivity(adminScreen);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Please fill out the required fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                }      }});


}}
