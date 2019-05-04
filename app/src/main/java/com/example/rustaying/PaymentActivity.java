package com.example.rustaying;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {
    private static final String TAG = "Payment Activity";
    private EditText answerBox1, answerBox2, answerBox3, answerBox4, answerBox5, answerBox6;
    private Spinner  exMonth,exYear;
    private Button paymentButton;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private String userID;
    private String[] arraySpinnerMonth;
    private String[] arraySpinnerYear=new String[50];

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
        Spinner spinnerX1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerX1.setAdapter(adapter1);

        Spinner spinnerX2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.years_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerX2.setAdapter(adapter2);


        answerBox1 = (EditText) findViewById(R.id.nameCard);
        answerBox2 = (EditText) findViewById(R.id.numCard);
        answerBox3 = (EditText) findViewById(R.id.ccvCard);
        answerBox4 = (EditText) findViewById(R.id.billingStreetAdd);
        answerBox5 = (EditText) findViewById(R.id.cityAdd);
        answerBox6 = (EditText) findViewById(R.id.zipAdd);
        exMonth = findViewById(R.id.spinner1);
        exYear = findViewById(R.id.spinner2);

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
                final String spin1 = exMonth.getSelectedItem().toString().trim();//month
                final String spin2 = exYear.getSelectedItem().toString().trim();//date
                final String spin = spin1+"/"+spin2;

                if (!TextUtils.isEmpty(answer1) && !TextUtils.isEmpty(answer2) && !TextUtils.isEmpty(answer3)
                    && !TextUtils.isEmpty(answer4) && !TextUtils.isEmpty(answer5) && !TextUtils.isEmpty(answer6)
                        && !TextUtils.isEmpty(spin1) && !TextUtils.isEmpty(spin2)){

                    Map<String,Object> list = new HashMap<>();
                    list.put("nameOnCCard", answer1);
                    list.put("creditCardNumber", answer2);
                    list.put("CCV", answer3);
                    list.put("billingAddress", answer4 + "," + answer5 + "," +answer6);
                    list.put("expirationDate", spin);

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