/************************
 Authors:
 Mohammed Sapin
 Nga Man (Mandy) Cheng
 Purna Haque
 *************************/


package com.example.rustaying;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminServiceList extends AppCompatActivity {

    private static final String TAG = "AdminServiceList";

    //XML attributes
    private Button bellboy, valet, maint, roomService, foodService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_service_list);

        //XML definitions
        //Set buttons for each service
        bellboy = (Button) findViewById(R.id.bellboy_requests);
        valet = (Button) findViewById(R.id.valet_travel);
        maint = (Button) findViewById(R.id.maintenance_request);
        roomService = (Button) findViewById(R.id.room_Service);
        foodService = (Button) findViewById(R.id.food_Service);

        //Open intent based on button code
        bellboy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //view bellboy services requests
                startActivity(new Intent(AdminServiceList.this, BellboyServices.class));
            }
        });

        valet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //view valet services requests
                startActivity(new Intent(AdminServiceList.this, ValetServices.class));
            }
        });

        maint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //view maintenance services requests
                startActivity(new Intent(AdminServiceList.this,MaintenanceServices.class));
            }
        });

        roomService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //view room services requests
                startActivity(new Intent(AdminServiceList.this, RoomServices.class));
            }
        });

        foodService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //view food services requests
                startActivity(new Intent(AdminServiceList.this, FoodServices.class));
            }
        });
    }
}
