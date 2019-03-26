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
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class FeedbackActivity extends AppCompatActivity {
    private static final String TAG = "Feedback Activity";
    private EditText answerBox1, answerBox2;
    private RatingBar starBar;
    private Button submitButton;
    // private FirebaseDatabase mFirebaseDatabase;
    //private DatabaseReference feedbackDatabase;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

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
        answerBox1 = (EditText) findViewById(R.id.A1);
        answerBox2 = (EditText) findViewById(R.id.A2);
        starBar = (RatingBar) findViewById(R.id.starBar);
        submitButton = (Button) findViewById(R.id.submitButton);

        //feedbackDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float notNull =0;

                notNull=starBar.getRating();

                final String answer1 = answerBox1.getText().toString().trim();
                final String answer2 = answerBox2.getText().toString().trim();
                final float rating=notNull;




                if (!TextUtils.isEmpty(answer1) && !TextUtils.isEmpty(answer2) && notNull!=0.0) {
                    Feedback feedbackInfo = new Feedback(rating, answer1, answer2);
                    myRef.child("Feedback").child(userID).setValue(feedbackInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(FeedbackActivity.this, "Feedback Submitted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(FeedbackActivity.this, HomeActivity.class));
                            finish();
                        }
                    });

                } else{
                    Toast.makeText(FeedbackActivity.this,"Please fill out the required fields",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
