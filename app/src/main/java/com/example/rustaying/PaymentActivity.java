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

public class FeedbackActivity extends AppCompatActivity {

    public class PaymentActivity {
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
            );
        }
    }
}