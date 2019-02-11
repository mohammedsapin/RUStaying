package com.example.rustaying;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    public void LoginFunction(View v){
        Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
    }

    public void RegisterFunction (View v){
        Log.i("Info","Register button pressed");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
