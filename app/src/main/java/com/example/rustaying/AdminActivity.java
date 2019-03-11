package com.example.rustaying;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {

//    DatabaseHelper db;
//    Button viewUsersButton;
//
//    public void showData(String title,String message){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(true);
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.show();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

//        db = new DatabaseHelper(this);
//        viewUsersButton = (Button) findViewById(R.id.viewUserBtn);
//
//        viewUsersButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Cursor view = db.getUsers();
//                if(view.getCount() == 0){
//                    Toast.makeText(AdminActivity.this,"Error, Nothing Found",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                StringBuffer buffer = new StringBuffer();
//                while (view.moveToNext()){
//                    buffer.append("ID: " + view.getString(0) + "\n");
//                    buffer.append("First Name: " + view.getString(1) + "\n");
//                    buffer.append("Last Name: " + view.getString(2) + "\n");
//                    buffer.append("Email: " + view.getString(3) + "\n");
//                }
//                showData("Data", buffer.toString());
//            }
//        });
    }
}
