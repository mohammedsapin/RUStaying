package com.example.rustaying;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    Button logout;
    Button bkRmBtn;

    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_account:
                        Intent account = new Intent(HomeActivity.this,ProfileActivity.class);
                        startActivity(account);
                        break;
                    case R.id.navigation_home:
                        break;
                    case R.id.navigation_services:
                        Intent services = new Intent(HomeActivity.this,ServicesActivity.class);
                        startActivity(services);
                        break;
                }
                return false;
            }
        });

        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user == null){
                    startActivity(new Intent(HomeActivity.this,MainActivity.class));
                    finish();
                }
            }
        };

        logout = (Button) findViewById(R.id.logoutBtn);

        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Creating dialog box to confirm if user wants to logout
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
                alertDialog.setMessage("Do you want to logout?")
                        //Positive button is Yes, meaning the use wants to logout
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        })
                        //Negative button is No, meaning user does not want to logout
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel(); //Close dialog box. Nothing happens
                            }
                        });


                    AlertDialog alert = alertDialog.create();
                    alert.setTitle("Confirm Logout");
                    alert.show();

                    //Changing colors of the Yes and No buttons
                    Button negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                    negativeButton.setTextColor(Color.RED);
                    negativeButton.setBackgroundColor(Color.WHITE);

                    Button positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                    positiveButton.setTextColor(Color.RED);
                    positiveButton.setBackgroundColor(Color.WHITE);
            }
        });

        bkRmBtn = (Button) findViewById(R.id.bkRmBtn);
        bkRmBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent reservationPage = new Intent(HomeActivity.this,ReservationActivity.class);
                startActivity(reservationPage); //Redirect to main page
            }
        }
        );

    }

    public void logout(){
        Toast.makeText(HomeActivity.this,"Logged Out",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(HomeActivity.this,MainActivity.class));
        finish();
        auth.signOut();
    }

    @Override
    public void onStart(){
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if (authStateListener != null){
            auth.removeAuthStateListener(authStateListener);
        }
    }
}
