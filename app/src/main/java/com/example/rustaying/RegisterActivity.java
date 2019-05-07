/************************
 Authors:
 Zain Sayed
 Keya Patel
 *************************/

package com.example.rustaying;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "Register Activity";

    private EditText firstNameInput, lastNameInput, emailInput, regPassInput,confirmPass;
    private Button regBtn;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstNameInput = (EditText) findViewById(R.id.firstname);
        lastNameInput = (EditText) findViewById(R.id.lastname);
        emailInput = (EditText) findViewById(R.id.emailinput);
        regPassInput = (EditText) findViewById(R.id.regPassInput);
        confirmPass = (EditText) findViewById(R.id.confirmPass);
        regBtn = (Button) findViewById(R.id.regBtn);

        auth = FirebaseAuth.getInstance();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstName = firstNameInput.getText().toString().trim();
                final String lastName = lastNameInput.getText().toString().trim();
                final String email = emailInput.getText().toString().trim();
                String password = regPassInput.getText().toString().trim(); // password has to be > 6 characters firebase rules
                String confirmPassword = confirmPass.getText().toString().trim();// password has to be > 6 characters firebase rules
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)) {
                    if (password.equals(confirmPassword)) {
                        auth.createUserWithEmailAndPassword(email,password)
                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            Guest guest = new Guest(
                                                    firstName,
                                                    lastName,
                                                    email
                                            );
                                            FirebaseDatabase.getInstance().getReference("Guest").child(auth.getCurrentUser().getUid())
                                                    .setValue(guest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(RegisterActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                        finish();
                                                    }
                                                    else {
                                                        Toast.makeText(RegisterActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }else{
                                            Toast.makeText(RegisterActivity.this,"Could not add guest",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }else{
                        Toast.makeText(RegisterActivity.this,"Passwords do not match",Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(RegisterActivity.this,"Please fill out the required fields",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

