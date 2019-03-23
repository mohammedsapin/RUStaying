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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText emInput, passInput;
    private Button loginBtn;
    private TextView registerBtn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        emInput = (EditText) findViewById(R.id.emInput);
        passInput = (EditText) findViewById(R.id.passInput);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        registerBtn = (TextView)findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerPage = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerPage);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emInput.getText().toString().trim();
                final String password = passInput.getText().toString().trim();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //admin is saved in database already do not register new account for admin
                                        //admin email == admin@mail.com
                                        //admin password == admin12345
                                        if (email.equals("admin@mail.com")) {
                                            Toast.makeText(MainActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();
                                            Intent adminPage = new Intent(MainActivity.this, AdminActivity.class);
                                            startActivity(adminPage);
                                        } else {
                                            Toast.makeText(MainActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();
                                            Intent homePage = new Intent(MainActivity.this, HomeActivity.class);
                                            startActivity(homePage);
                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "Login Failed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(MainActivity.this, "Please fill out the required fields",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

