/************************
 Authors:
 Keya Patel
 Zain Sayed
 *************************/

package com.example.rustaying;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackActivity extends AppCompatActivity {
    private static final String TAG = "Feedback Activity";
    private EditText answerBox1;
    private RatingBar ratingBar1,ratingBar2,ratingBar3,ratingBar4,ratingBar5;
    private CheckBox cBox1, cBox2, cBox3, cBox4;
    private Switch switch1;
    private Button submitButton;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        //navigation bar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_account:
                        Intent account = new Intent(FeedbackActivity.this,ProfileActivity.class);
                        startActivity(account);
                        break;
                    case R.id.navigation_home:
                        Intent home = new Intent(FeedbackActivity.this,HomeActivity.class);
                        startActivity(home);
                        break;
                    case R.id.navigation_services:
                        break;
                }
                return false;
            }
        });
        //reads in all the feedback inputs from the user
        ratingBar1 = (RatingBar) findViewById(R.id.rating1);
        ratingBar2 = (RatingBar) findViewById(R.id.rating2);
        ratingBar3 = (RatingBar) findViewById(R.id.rating3);
        ratingBar4 = (RatingBar) findViewById(R.id.rating4);
        ratingBar5 = (RatingBar) findViewById(R.id.rating5);
        answerBox1 = (EditText) findViewById(R.id.answer1);
        cBox1 = (CheckBox) findViewById(R.id.cb1);
        cBox2 = (CheckBox) findViewById(R.id.cb2);
        cBox3 = (CheckBox) findViewById(R.id.cb3);
        cBox4 = (CheckBox) findViewById(R.id.cb4);
        switch1 = (Switch)findViewById(R.id.switch1);

        submitButton = (Button) findViewById(R.id.submitButton);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //converting all the floats to string types to make it easier for us to store into Firebase
                final String answer1 = answerBox1.getText().toString().trim();
                final String rating1= Float.toString(ratingBar1.getRating());
                final String rating2= Float.toString(ratingBar2.getRating());
                final String rating3= Float.toString(ratingBar3.getRating());
                final String rating4= Float.toString(ratingBar4.getRating());
                final String rating5= Float.toString(ratingBar5.getRating());

                final boolean check1 =cBox1.isChecked();
                final boolean check2 =cBox2.isChecked();
                final boolean check3 =cBox3.isChecked();
                final boolean check4 =cBox4.isChecked();
                final boolean switch1Checked = switch1.isChecked();

                //checks for the required field
                if (ratingBar1.getRating() > 0){
                    Feedback feedbackInfo = new Feedback(rating1, rating2, rating3, rating4, rating5, answer1,
                            check1, check2, check3, check3, switch1Checked);
                    myRef.child("Feedback").child(userID).setValue(feedbackInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(FeedbackActivity.this, "Feedback Submitted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(FeedbackActivity.this, HomeActivity.class));
                            finish();
                        }
                    });

                }else{
                    Toast.makeText(FeedbackActivity.this,"Please fill out the required fields",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}