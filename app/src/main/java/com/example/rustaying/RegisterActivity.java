package com.example.rustaying;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText emailInput;
    EditText regPassInput;
    EditText confirmPass;
    Button regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        emailInput = (EditText) findViewById(R.id.emailinput);
        regPassInput = (EditText) findViewById(R.id.regPassInput);
        confirmPass = (EditText) findViewById(R.id.confirmPass);
        regBtn = (Button) findViewById(R.id.regBtn);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString().trim();
                String password = regPassInput.getText().toString().trim();
                String confirmPassword = confirmPass.getText().toString().trim();

                if (password.equals(confirmPassword)){
                    long user = db.addUser(email,password);
                    if (user > 0){
                        Toast.makeText(RegisterActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                        Intent loginPage = new Intent(RegisterActivity.this,MainActivity.class);
                        startActivity(loginPage);
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this,"User already exists",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this,"Passwords do not match",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

