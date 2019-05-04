package com.example.rustaying;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText emInput, passInput;
    private Button loginBtn;
    private TextView registerBtn;
    private FirebaseAuth auth;
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        emInput = (EditText) findViewById(R.id.emInput);
        passInput = (EditText) findViewById(R.id.passInput);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        forgotPassword = (TextView)findViewById(R.id.resetPassword);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emInput.getText().toString().trim();
                final String password = passInput.getText().toString().trim();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //admin is saved in database already do not register new account for admin
                                        //admin email == admin@mail.com
                                        //admin password == admin12345
                                        if (email.equals("admin@mail.com")) {
                                            Toast.makeText(LoginActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();
                                            Intent adminPage = new Intent(LoginActivity.this, AdminActivity.class);
                                            startActivity(adminPage);
                                        }
                                        else if (email.equals("bellboyadmin@mail.com")) {
                                            Toast.makeText(LoginActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();
                                            Intent adminPage = new Intent(LoginActivity.this, BellboyAdmin.class);
                                            startActivity(adminPage);
                                        }
                                        else if (email.equals("valetadmin@mail.com")) {
                                            Toast.makeText(LoginActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();
                                            Intent adminPage = new Intent(LoginActivity.this, ValetAdmin.class);
                                            startActivity(adminPage);
                                        }
                                        else if (email.equals("maintenanceadmin@mail.com")) {
                                            Toast.makeText(LoginActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();
                                            Intent adminPage = new Intent(LoginActivity.this, MaintenanceAdmin.class);
                                            startActivity(adminPage);
                                        }
                                        else if (email.equals("roomservicesadmin@mail.com")) {
                                            Toast.makeText(LoginActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();
                                            Intent adminPage = new Intent(LoginActivity.this,
                                                    RoomServiceAdmin.class);
                                            startActivity(adminPage);
                                        }
                                        else if (email.equals("foodadmin@mail.com")) {
                                            Toast.makeText(LoginActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();
                                            Intent adminPage = new Intent(LoginActivity.this,
                                                    FoodServiceAdmin.class);
                                            startActivity(adminPage);
                                        }
                                        else {
                                            Toast.makeText(LoginActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();
                                            Intent homePage = new Intent(LoginActivity.this, HomeActivity.class);
                                            startActivity(homePage);
                                        }
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Login Failed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(LoginActivity.this, "Please fill out the required fields",Toast.LENGTH_SHORT).show();
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,PasswordActivity.class));
            }
        });

    }
}

