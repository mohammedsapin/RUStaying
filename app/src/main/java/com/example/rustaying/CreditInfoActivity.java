/************************
 Authors:
 Keya Patel
 Zain Sayed
 *************************/

package com.example.rustaying;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreditInfoActivity extends AppCompatActivity {

    private static final String TAG = "CreditInfoActivity";
    private EditText creditNumber, CCV, newName;
    private Button submitButton;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private String userID;
    private Spinner expMonth,expYear;
    private String[] arraySpinnerMonth;
    private String[] arraySpinnerYear=new String[50];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_info);

        //SPINNERS POPULATED HERE
        int count = 0;
        for(int x = 2019;x<2069;x++){
            this.arraySpinnerYear[count]=""+x;
            count++;
        }
        Spinner spinnerYear= (Spinner)findViewById(R.id.date_year);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arraySpinnerYear);
        spinnerYear.setAdapter(adapter);


        this.arraySpinnerMonth=new String[]{
          "1","2","3","4","5","6","7","8","9","10","11","12"
        };
        Spinner spinnerMonth= (Spinner)findViewById(R.id.date_month);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arraySpinnerMonth);
        spinnerMonth.setAdapter(adapter2);
        //END OF SPINNER POPULATION

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_account:
                        Intent account = new Intent(CreditInfoActivity.this,ProfileActivity.class);
                        startActivity(account);
                        finish();
                        break;
                    case R.id.navigation_home:
                        Intent home = new Intent(CreditInfoActivity.this,HomeActivity.class);
                        startActivity(home);
                        finish();
                        break;
                    case R.id.navigation_services:
                        Intent service = new Intent(CreditInfoActivity.this, ServicesActivity.class);
                        startActivity(service);
                        finish();
                        break;
                }
                return false;
            }
        });


        creditNumber = findViewById(R.id.creditNumber);
        CCV =  findViewById(R.id.CCV_number);
        newName = findViewById(R.id.nameID);
        expMonth = findViewById(R.id.date_month);
        expYear = findViewById(R.id.date_year);
        submitButton= findViewById(R.id.submitButton);


        mAuth = FirebaseAuth.getInstance(); //mAuth
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference(); //dbRef
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String answer1 = creditNumber.getText().toString().trim();
                final String answer2 = CCV.getText().toString().trim();
                final String answer3 = newName.getText().toString().trim();
                final String answer4 = expMonth.getSelectedItem().toString();
                final String answer5 = expYear.getSelectedItem().toString();
                final String answer6 = answer4+"/"+answer5;

                Map<String,Object> list = new HashMap<>();

                if(!answer1.isEmpty() && !answer2.isEmpty() &&!answer3.isEmpty()&& !answer6.isEmpty()) {
                    list.put("creditCardNumber", answer1); //
                    list.put("CCV", answer2);
                    list.put("nameOnCCard", answer3);
                    list.put("expirationDate",answer6);

                    myRef.child("Guest").child(userID).updateChildren(list).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(CreditInfoActivity.this, " Credit Info updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CreditInfoActivity.this, CreditActivity.class));
                                finish();

                            }else{
                                Toast.makeText(CreditInfoActivity.this, " Credit Info update was not successful, please try again", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CreditInfoActivity.this, CreditInfoActivity.class));
                                finish();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(CreditInfoActivity.this, "Please input all required fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });






    }

}
