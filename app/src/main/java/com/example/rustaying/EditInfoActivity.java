package com.example.rustaying;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditInfoActivity extends AppCompatActivity {

    private static final String TAG = "EditInfoActivity";
    private EditText first_name, last_name, emailID;
    private Button submitButton;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_account:
                        Intent account = new Intent(EditInfoActivity.this,ProfileActivity.class);
                        startActivity(account);
                        break;
                    case R.id.navigation_home:
                        Intent home = new Intent(EditInfoActivity.this,HomeActivity.class);
                        startActivity(home);
                        break;
                    case R.id.navigation_services:
                        Intent service = new Intent(EditInfoActivity.this, ServicesActivity.class);
                        startActivity(service);
                        break;
                }
                return false;
            }
        });


        first_name = findViewById(R.id.firstname);
        last_name =  findViewById(R.id.lastname);
        emailID = findViewById(R.id.emailID);
        submitButton= findViewById(R.id.submitButton);


        mAuth = FirebaseAuth.getInstance(); //mAuth
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference(); //dbRef
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String answer1 = first_name.getText().toString().trim();
                final String answer2 = last_name.getText().toString().trim();
                final String answer3 = emailID.getText().toString().trim();


                    Map<String,Object> list = new HashMap<>();

                    if(!answer1.isEmpty()) {
                        list.put("firstName", answer1);
                    }

                    if(!answer2.isEmpty()) {
                        list.put("lastName", answer2);
                    }

                    if(!answer3.isEmpty()) {
                        list.put("guestEmail", answer3);
                    }



                    myRef.child("Guest").child(userID).updateChildren(list).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(EditInfoActivity.this, "Info updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditInfoActivity.this, ProfileActivity.class));
                                finish();

                            }else{
                                startActivity(new Intent(EditInfoActivity.this, ProfileActivity.class));
                                finish();
                            }
                        }
                    });




            }
        });






    }
}
