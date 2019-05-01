package com.example.rustaying;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {
    private static final String TAG = "Payment Activity";
    private EditText answerBox1, answerBox2, answerBox3, answerBox4, answerBox5, answerBox6, spinner1,spinner2;
    private Button paymentButton;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private String userID;
    private Guest g = new Guest();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_account:
                        Intent account = new Intent(PaymentActivity.this,ProfileActivity.class);
                        startActivity(account);
                        break;
                    case R.id.navigation_home:
                        Intent home = new Intent(PaymentActivity.this,HomeActivity.class);
                        startActivity(home);
                        break;
                    case R.id.navigation_services:
                        break;
                }
                return false;
            }
        });

        answerBox1 = (EditText) findViewById(R.id.nameCard);
        answerBox2 = (EditText) findViewById(R.id.numCard);
        answerBox3 = (EditText) findViewById(R.id.ccvCard);
        answerBox4 = (EditText) findViewById(R.id.billingStreetAdd);
        answerBox5 = (EditText) findViewById(R.id.cityAdd);
        answerBox6 = (EditText) findViewById(R.id.zipAdd);
        //spinner1 = (EditText) findViewById(R.id.spinner1);
        //spinner2 = (EditText) findViewById(R.id.spinner2);

        paymentButton = (Button) findViewById(R.id.button);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String answer1 = answerBox1.getText().toString().trim();// name
                final String answer2 = answerBox2.getText().toString().trim();//number
                final String answer3 = answerBox3.getText().toString().trim();//ccv
                final String answer4 = answerBox4.getText().toString().trim();//street
                final String answer5 = answerBox5.getText().toString().trim();//city
                final String answer6 = answerBox6.getText().toString().trim();//zip
                //final String spin1 = spinner1.getText().toString().trim();//month
                //final String spin2 = spinner2.getText().toString().trim();//date


                if (!TextUtils.isEmpty(answer1) && !TextUtils.isEmpty(answer2) && !TextUtils.isEmpty(answer3)){
                    Map<String,Object> list = new HashMap<>();
                    /*
                    if(TextUtils.isEmpty(g.getNameOnCCard())){
                        list.put("nameOnCCard", answer1);
                    }*/


                    if(!answer1.isEmpty()) {
                        list.put("nameOnCCard", answer1);
                    }
                    if(!answer2.isEmpty()) {
                        list.put("creditCardNumber", answer1);
                    }
                    if(!answer3.isEmpty()) {
                        list.put("CCV", answer1);
                    }
                    if(!answer4.isEmpty()) {
                        list.put("billingAddress", answer4 + "," + answer5 + "," +answer6);
                    }
                    list.put("validPayment", true);

                    myRef.child("Guest").child(userID).updateChildren(list).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(PaymentActivity.this, "Payment Confirmed", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(PaymentActivity.this, HomeActivity.class));
                                finish();
                            }
                            else{
                                Toast.makeText(PaymentActivity.this,"Please fill out the required fields",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    //end editing here
                }
            }
        });
    }
}