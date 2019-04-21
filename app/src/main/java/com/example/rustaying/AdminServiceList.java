package com.example.rustaying;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminServiceList extends AppCompatActivity {

    private static final String TAG = "AdminServiceList";
    private Button bellboy, valet, maint, roomService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_service_list);

        bellboy = (Button) findViewById(R.id.bellboy_requests);
        valet = (Button) findViewById(R.id.valet_travel);
        maint = (Button) findViewById(R.id.maintenance_request);
        roomService = (Button) findViewById(R.id.room_Service);

        bellboy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminServiceList.this, ViewServices.class));
            }
        });

        valet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        maint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        roomService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
