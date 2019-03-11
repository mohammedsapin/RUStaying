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
    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
    EditText regPassInput;
    EditText confirmPass;
    Button regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        firstNameInput = (EditText) findViewById(R.id.inputFIrstName);
        lastNameInput = (EditText) findViewById(R.id.inputLastName);
        emailInput = (EditText) findViewById(R.id.emailinput);
        regPassInput = (EditText) findViewById(R.id.regPassInput);
        confirmPass = (EditText) findViewById(R.id.confirmPass);
        regBtn = (Button) findViewById(R.id.regBtn);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameInput.getText().toString().trim();
                String lastName = lastNameInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                String password = regPassInput.getText().toString().trim();
                String confirmPassword = confirmPass.getText().toString().trim();
                System.out.println(firstName);
                System.out.println(lastName);
                System.out.println(email);
                System.out.println(password);
                if (db.isEmpty(emailInput) != true && db.isEmpty(regPassInput) != true && db.isEmpty(confirmPass) != true){
                    if (password.equals(confirmPassword)){
                        long user = db.addUser(firstName, lastName, email, password);
                        System.out.println(user);
                        if (user > 0){ //Condition for successful registration
                            //Guest g = new Guest(email, password); //Creating new guest object
                            Toast.makeText(RegisterActivity.this,"Registration Successful " + firstName,Toast.LENGTH_SHORT).show();
                            Intent loginPage = new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(loginPage);
                            finish();
                        }else{ //Not always true. user will be -1 if there is any sort of error inserting the user to the db table
                            Toast.makeText(RegisterActivity.this,"User already exists",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this,"Passwords do not match",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText((RegisterActivity.this),"Please fill out the required fields",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

