package com.example.rustaying;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminActivity extends AppCompatActivity {

    private static final String TAG = "AdminActivity";

    //Firebase Stuff
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    //XML Attributes
    Button viewRooms, viewGuests, viewServices, viewData, log_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Firebase Objects and Methods
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null){
                    startActivity(new Intent(AdminActivity.this, LoginActivity.class ));
                    Log.d(TAG, "onAuthStateChanged: Signed out");
                    finish();
                }
            }
        };

        //XML Objects and Methods
        viewRooms = (Button) findViewById(R.id.viewRoomBtn);
        viewGuests = (Button) findViewById(R.id.viewGuestsBtn);
        viewServices = (Button) findViewById(R.id.viewServicesBtn);
        viewData = (Button) findViewById(R.id.viewDataBtn);
        log_out = (Button) findViewById(R.id.adminLogout);

        viewRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, newViewRooms.class));
            }
        });

        viewGuests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, ViewGuests.class));
            }
        });

        viewServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this,AdminServiceList.class));
            }
        });

        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating dialog box to confirm if user wants to logout
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
                alertDialog.setMessage("Do you want to logout?")
                        //Positive button is Yes, meaning the use wants to logout
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AdminActivity.this,"Logged out",Toast.LENGTH_SHORT).show();
                                mAuth.signOut();
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
