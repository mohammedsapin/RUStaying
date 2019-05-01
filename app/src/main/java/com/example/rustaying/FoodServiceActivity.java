package com.example.rustaying;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class FoodServiceActivity extends AppCompatActivity {
    Service foodservice = new Service();

    private static final String TAG = "FoodServiceActivity";
    private Button back;
    private Button submitButton;

    DatePickerDialog dateDialog;

    Button dateBtn1;
    Button viewBtn;
    TextView date1;
    Calendar c;

    private EditText answerBox1;

    LocalDate requestDate; //LocalDate object used in ResInfo object


    //
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_service);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_account:
                        Intent account = new Intent(FoodServiceActivity.this,ProfileActivity.class);
                        startActivity(account);
                        break;
                    case R.id.navigation_home:
                        Intent home = new Intent(FoodServiceActivity.this,HomeActivity.class);
                        startActivity(home);
                        break;
                    case R.id.navigation_services:
                        Intent services = new Intent(FoodServiceActivity.this,
                                ServicesActivity.class);
                        startActivity(services);
                        break;
                }
                return false;
            }
        });
        back = (Button) findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ba = new Intent(FoodServiceActivity.this, ServicesActivity.class);
                startActivity(ba);
            }
        });

        Spinner hours = (Spinner) findViewById(R.id.hours);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.hours));
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hours.setAdapter(adapter1);
        //  String hourValue, minuteValue, ampmValue, numb
        hours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String hourValue = parent.getItemAtPosition(position).toString();
                foodservice.setHourValue(hourValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dateBtn1 = (Button) findViewById(R.id.calendarBtn1);
        viewBtn = (Button) findViewById(R.id.viewRoomsBtn);

        date1 = (TextView) findViewById(R.id.requestDateR);


        dateBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                final LocalDate currentDate = parseDate(year, (month + 1), day);
                dateDialog = new DatePickerDialog(FoodServiceActivity.this, R.style.Theme_AppCompat, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth1) {
                        requestDate = parseDate(year1, (month1 + 1), dayOfMonth1);
                        String requestDate1=requestDate.toString();
                        foodservice.setRequestDate(requestDate1);

                        if(requestDate.compareTo(currentDate) < 0)
                        {
                            Toast.makeText(FoodServiceActivity.this, "Invalid Date",
                                    Toast.LENGTH_SHORT).show();
                            requestDate = null;
                        }
                        else
                        {
                            date1.setText((month1 + 1) + "/" + dayOfMonth1 + "/" + year1);
                        }

                    }
                }, year, month, day);
                dateDialog.show();
            }
        });




        Spinner minutes = (Spinner) findViewById(R.id.minutes);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.minutes));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minutes.setAdapter(adapter2);
        minutes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String minuteValue = parent.getItemAtPosition(position).toString();
                foodservice.setMinuteValue(minuteValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner ampm = (Spinner) findViewById(R.id.ampm);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.ampm));
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ampm.setAdapter(adapter3);
        // String ampmValue=" ";
        ampm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ampmValue = parent.getItemAtPosition(position).toString();
                foodservice.setAmpmValue(ampmValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        foodservice.setFoodPrice(0);
        foodservice.setA1(0);
        foodservice.setA2(0);
        foodservice.setM1(0);
        foodservice.setM2(0);
        foodservice.setM3(0);
        foodservice.setM4(0);
        foodservice.setD1(0);
        foodservice.setD2(0);
        foodservice.setDr1(0);
        foodservice.setDr2(0);
        foodservice.setDr3(0);


        Spinner app1S = (Spinner) findViewById(R.id.app1S);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.app1S));
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        app1S.setAdapter(adapter4);
        // String ampmValue=" ";
        app1S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer calc1 = foodservice.getFoodPrice();
                if (foodservice.getA1()>position){ //ordering less than original
                    Integer change = foodservice.getA1()-position;
                    foodservice.setFoodPrice(calc1-change*3);
                }
                else{
                    Integer change = position-foodservice.getA1();
                    foodservice.setFoodPrice(calc1+change*3);
                }
                foodservice.setA1(position);
                EditText editText= (EditText) findViewById(R.id.foodprice);
                editText.setText(foodservice.getFoodPrice().toString());

                String app1SValue = parent.getItemAtPosition(position).toString();
                foodservice.setGardenSalad(app1SValue);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner app2S = (Spinner) findViewById(R.id.app2S);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.app2S));
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        app2S.setAdapter(adapter5);
        // String ampmValue=" ";
        app2S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer calc1 = foodservice.getFoodPrice();
                if (foodservice.getA2()>position){ //ordering less than original
                    Integer change = foodservice.getA2()-position;
                    foodservice.setFoodPrice(calc1-change*3);
                }
                else{
                    Integer change = position-foodservice.getA2();
                    foodservice.setFoodPrice(calc1+change*3);
                }
                foodservice.setA2(position);
                EditText editText= (EditText) findViewById(R.id.foodprice);
                editText.setText(foodservice.getFoodPrice().toString());


                String app2SValue = parent.getItemAtPosition(position).toString();
                foodservice.setTomatoSoup(app2SValue);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner main1S = (Spinner) findViewById(R.id.main1S);
        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.main1S));
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        main1S.setAdapter(adapter6);
        // String ampmValue=" ";
        main1S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer calc1 = foodservice.getFoodPrice();
                if (foodservice.getM1()>position){ //ordering less than original
                    Integer change = foodservice.getM1()-position;
                    foodservice.setFoodPrice(calc1-change*5);
                }
                else{
                    Integer change = position-foodservice.getM1();
                    foodservice.setFoodPrice(calc1+change*5);
                }
                foodservice.setM1(position);
                EditText editText= (EditText) findViewById(R.id.foodprice);
                editText.setText(foodservice.getFoodPrice().toString());


                String main1SValue = parent.getItemAtPosition(position).toString();
                foodservice.setFriedChicken(main1SValue);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner main2S = (Spinner) findViewById(R.id.main2S);
        ArrayAdapter<String> adapter7 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.main2S));
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        main2S.setAdapter(adapter7);
        // String ampmValue=" ";
        main2S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer calc1 = foodservice.getFoodPrice();

                if (foodservice.getM2()>position){ //ordering less than original
                    Integer change = foodservice.getM2()-position;
                    foodservice.setFoodPrice(calc1-change*5);
                }
                else{
                    Integer change = position-foodservice.getM2();
                    foodservice.setFoodPrice(calc1+change*5);
                }
                foodservice.setM2(position);
                EditText editText= (EditText) findViewById(R.id.foodprice);
                editText.setText(foodservice.getFoodPrice().toString());


                String main2SValue = parent.getItemAtPosition(position).toString();
                foodservice.setCheesePizza(main2SValue);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner main3S = (Spinner) findViewById(R.id.main3S);
        ArrayAdapter<String> adapter8 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.main3S));
        adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        main3S.setAdapter(adapter8);
        main3S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer calc1 = foodservice.getFoodPrice();

                if (foodservice.getM3()>position){ //ordering less than original
                    Integer change = foodservice.getM3()-position;
                    foodservice.setFoodPrice(calc1-change*5);
                }
                else{
                    Integer change = position-foodservice.getM3();
                    foodservice.setFoodPrice(calc1+change*5);
                }
                foodservice.setM3(position);
                EditText editText= (EditText) findViewById(R.id.foodprice);
                editText.setText(foodservice.getFoodPrice().toString());


                String main3SValue = parent.getItemAtPosition(position).toString();
                foodservice.setSpaghetti(main3SValue);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner main4S = (Spinner) findViewById(R.id.main4S);
        ArrayAdapter<String> adapter9 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.main4S));
        adapter9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        main4S.setAdapter(adapter9);
        main4S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer calc1 = foodservice.getFoodPrice();

                if (foodservice.getM4()>position){ //ordering less than original
                    Integer change = foodservice.getM4()-position;
                    foodservice.setFoodPrice(calc1-change*5);
                }
                else{
                    Integer change = position-foodservice.getM4();
                    foodservice.setFoodPrice(calc1+change*5);
                }
                foodservice.setM4(position);
                EditText editText= (EditText) findViewById(R.id.foodprice);
                editText.setText(foodservice.getFoodPrice().toString());


                String main4SValue = parent.getItemAtPosition(position).toString();
                foodservice.setMacAndCheese(main4SValue);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner des1S = (Spinner) findViewById(R.id.des1S);
        ArrayAdapter<String> adapter10 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.des1S));
        adapter10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        des1S.setAdapter(adapter10);
        des1S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer calc1 = foodservice.getFoodPrice();

                if (foodservice.getD1()>position){ //ordering less than original
                    Integer change = foodservice.getD1()-position;
                    foodservice.setFoodPrice(calc1-change*2);
                }
                else{
                    Integer change = position-foodservice.getD1();
                    foodservice.setFoodPrice(calc1+change*2);
                }
                foodservice.setD1(position);
                EditText editText= (EditText) findViewById(R.id.foodprice);
                editText.setText(foodservice.getFoodPrice().toString());


                String des1SValue = parent.getItemAtPosition(position).toString();
                foodservice.setVanillaIceCream(des1SValue);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner des2S = (Spinner) findViewById(R.id.des2S);
        ArrayAdapter<String> adapter11 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.des2S));
        adapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        des2S.setAdapter(adapter11);
        des2S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer calc1 = foodservice.getFoodPrice();

                if (foodservice.getD2()>position){ //ordering less than original
                    Integer change = foodservice.getD2()-position;
                    foodservice.setFoodPrice(calc1-change*2);
                }
                else{
                    Integer change = position-foodservice.getD2();
                    foodservice.setFoodPrice(calc1+change*2);
                }
                foodservice.setD2(position);
                EditText editText= (EditText) findViewById(R.id.foodprice);
                editText.setText(foodservice.getFoodPrice().toString());


                String des2SValue = parent.getItemAtPosition(position).toString();
                foodservice.setFruitCake(des2SValue);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Spinner drink1S = (Spinner) findViewById(R.id.drink1S);
        ArrayAdapter<String> adapter12 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.drink1S));
        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drink1S.setAdapter(adapter12);
        drink1S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer calc1 = foodservice.getFoodPrice();

                if (foodservice.getDr1()>position){ //ordering less than original
                    Integer change = foodservice.getDr1()-position;
                    foodservice.setFoodPrice(calc1-change*1);
                }
                else{
                    Integer change = position-foodservice.getDr1();
                    foodservice.setFoodPrice(calc1+change*1);
                }
                foodservice.setDr1(position);
                EditText editText= (EditText) findViewById(R.id.foodprice);
                editText.setText(foodservice.getFoodPrice().toString());


                String drink1SValue = parent.getItemAtPosition(position).toString();
                foodservice.setCoke(drink1SValue);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner drink2S = (Spinner) findViewById(R.id.drink2S);
        ArrayAdapter<String> adapter13 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.drink2S));
        adapter13.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drink2S.setAdapter(adapter13);
        drink2S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer calc1 = foodservice.getFoodPrice();

                if (foodservice.getDr2()>position){ //ordering less than original
                    Integer change = foodservice.getDr2()-position;
                    foodservice.setFoodPrice(calc1-change*1);
                }
                else{
                    Integer change = position-foodservice.getDr2();
                    foodservice.setFoodPrice(calc1+change*1);
                }
                foodservice.setDr2(position);
                EditText editText= (EditText) findViewById(R.id.foodprice);
                editText.setText(foodservice.getFoodPrice().toString());


                String drink2SValue = parent.getItemAtPosition(position).toString();
                foodservice.setSprite(drink2SValue);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner drink3S = (Spinner) findViewById(R.id.drink3S);
        ArrayAdapter<String> adapter14 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.drink3S));
        adapter14.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drink3S.setAdapter(adapter14);
        drink3S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer calc1 = foodservice.getFoodPrice();

                if (foodservice.getDr3()>position){ //ordering less than original
                    Integer change = foodservice.getDr3()-position;
                    foodservice.setFoodPrice(calc1-change*1);
                }
                else{
                    Integer change = position-foodservice.getDr3();
                    foodservice.setFoodPrice(calc1+change*1);
                }
                foodservice.setDr3(position);
                EditText editText= (EditText) findViewById(R.id.foodprice);
                editText.setText(foodservice.getFoodPrice().toString());


                String drink3SValue = parent.getItemAtPosition(position).toString();
                foodservice.setAppleJuice(drink3SValue);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        answerBox1 = (EditText) findViewById(R.id.A1);
        submitButton= (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                long random = 100000000 + rand.nextInt(900000000);
                foodservice.setRequestID(random);
                long requestID = foodservice.getRequestID();
                String request = Long.toString(requestID);

                final String answer1 = answerBox1.getText().toString().trim();
                String hourValue = foodservice.getHourValue();
                String minuteValue = foodservice.getMinuteValue();
                String ampmValue = foodservice.getAmpmValue();
                String requestedTimeFoodService = hourValue + ":" + minuteValue + " " + ampmValue;
                String requestType = "Food Service";
                String requestDate = foodservice.getRequestDate();


                String app1 = foodservice.getGardenSalad();
                String app2 = foodservice.getTomatoSoup();
                String main1 = foodservice.getFriedChicken();
                String main2 = foodservice.getCheesePizza();
                String main3 = foodservice.getSpaghetti();
                String main4 = foodservice.getMacAndCheese();
                String des1 = foodservice.getVanillaIceCream();
                String des2 = foodservice.getFruitCake();
                String drink1 = foodservice.getCoke();
                String drink2 = foodservice.getSprite();
                String drink3 = foodservice.getAppleJuice();

                Integer foodPrice = foodservice.getFoodPrice();

                if (Integer.parseInt(app1) == 0 && Integer.parseInt(app2) == 0 && Integer.parseInt(main1) == 0 &&
                        Integer.parseInt(main2) == 0 && Integer.parseInt(main3) == 0 && Integer.parseInt(main4) == 0 &&
                        Integer.parseInt(des1) == 0 && Integer.parseInt(des2) == 0 && Integer.parseInt(drink1) == 0 &&
                        Integer.parseInt(drink2) == 0 && Integer.parseInt(drink3) == 0) {
                    Toast.makeText(FoodServiceActivity.this, "Please choose at least 1 item to purchase!",Toast.LENGTH_SHORT).show();

                } else {
                    Service service = new Service(requestType, requestDate, requestedTimeFoodService, answer1,
                            app1, app2, main1, main2, main3, main4, des1, des2, drink1, drink2, drink3, foodPrice);
                    myRef.child("Service").child(userID).child(request).setValue(service).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(FoodServiceActivity.this, "Request Sent!", Toast.LENGTH_SHORT).show();
                            Intent submit = new Intent(FoodServiceActivity.this, ServicesActivity.class);
                            startActivity(submit); //Redirect to main page
                            finish();
                        }
                    });

                }
            }
        });




    }
    private LocalDate parseDate(int year, int month, int date)
    {
        return LocalDate.of(year, month, date);
    }
}


