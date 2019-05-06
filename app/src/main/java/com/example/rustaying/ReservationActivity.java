package com.example.rustaying;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Calendar;


public class ReservationActivity extends AppCompatActivity {

    private static final String TAG = "ReservationActivity";

    DatePickerDialog checkInDialog, checkOutDialog;

    Button dateBtn1;
    Button dateBtn2;
    Button viewBtn;
    TextView date1, date2; //Texts to display string
    Calendar c;

    LocalDate checkInDate, checkOutDate, currentDate; //LocalDate object used in ResInfo object
    ResInfo info; //Object to send to another activity

    CheckBox single, double1, queen, king;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);


        dateBtn1 = (Button) findViewById(R.id.calendarBtn1);
        dateBtn2 = (Button) findViewById(R.id.calendarBtn2);
        viewBtn = (Button) findViewById(R.id.viewRoomsBtn);

        date1 = (TextView) findViewById(R.id.checkInDate);
        date2 = (TextView) findViewById(R.id.checkOutDate);


        single = (CheckBox)findViewById(R.id.single);
        double1 = (CheckBox)findViewById(R.id.doubleTxt);
        queen = (CheckBox)findViewById(R.id.queen);
        king = (CheckBox)findViewById(R.id.king);

        dateBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                currentDate = parseDate(year,(month+1), day); //Current date of LocalDate object

                checkInDialog = new DatePickerDialog(ReservationActivity.this, R.style.Theme_AppCompat, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth1)
                    {
                        checkInDate = parseDate(year1, (month1+1), dayOfMonth1);
                        if(checkInDate.compareTo(currentDate) < 0)
                        {
                            Toast.makeText(ReservationActivity.this, "Invalid Date",Toast.LENGTH_SHORT).show();
                            checkInDate = null;
                        }
                        else if(checkOutDate != null && checkInDate.compareTo(checkOutDate) > 0)
                        {
                            Toast.makeText(ReservationActivity.this, "Invalid Date",Toast.LENGTH_SHORT).show();
                            checkInDate = null;
                        }
                        else
                        {
                            date1.setText((month1 + 1) + "/" +  dayOfMonth1 + "/" + year1);
                        }


                    }
                }, year, month, day);

                checkInDialog.show();
            }
        });

        dateBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                currentDate = parseDate(year,(month+1), day);

                checkOutDialog = new DatePickerDialog(ReservationActivity.this,
                                                        R.style.Theme_AppCompat,
                                                        new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth1)
                    {
                        checkOutDate = parseDate(year1, (month1+1), dayOfMonth1);

                        if(checkOutDate.compareTo(currentDate) < 0)
                        {
                            Toast.makeText(ReservationActivity.this, "Invalid Date",Toast.LENGTH_SHORT).show();
                            checkOutDate = null;
                        }
                        else if(checkInDate != null && checkOutDate.compareTo(checkInDate) < 1)
                        {
                            Toast.makeText(ReservationActivity.this, "Check out date cannot be before check in date",Toast.LENGTH_LONG).show();
                            checkOutDate = null;
                        }
                        else
                        {
                            date2.setText((month1 + 1) + "/" +  dayOfMonth1 + "/" + year1);
                        }

                    }
                }, year, month, (day+1));

                checkOutDialog.show();
            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] roomTypes = new String[4]; //String array of room types checked
                //Determine which checkboxes are checked
                if(single.isChecked())
                {
                    roomTypes[0] = "Single";
                }
                if(double1.isChecked())
                {
                    roomTypes[1] = "Double";
                }
                if(queen.isChecked())
                {
                    roomTypes[2] = "Queen";
                }
                if(king.isChecked())
                {
                    roomTypes[3] = "King";
                }

                if(checkInDate != null && checkOutDate != null &&
                        (single.isChecked() || double1.isChecked() || queen.isChecked() || king.isChecked()))
                {
                    //Check checkIn and checkOut dates to make sure they are not null

                    //Setup ResInfo object
                    info = new ResInfo(checkInDate, checkOutDate, roomTypes);


                    Intent viewRooms = new Intent(ReservationActivity.this, newViewRooms.class);

                    Bundle b = new Bundle();

                    b.putInt("inDay", info.getCheckIn().getDayOfMonth());
                    b.putInt("inMonth", info.getCheckIn().getMonth().getValue());
                    b.putInt("inYear", info.getCheckIn().getYear());

                    b.putInt("outDay", info.getCheckOut().getDayOfMonth());
                    b.putInt("outMonth", info.getCheckOut().getMonth().getValue());
                    b.putInt("outYear", info.getCheckOut().getYear());

                    b.putString("checkIn", info.getCheckIn().toString());
                    b.putString("checkOut", info.getCheckOut().toString());
                    b.putStringArray("roomTypes", info.getRoomTypes());

                    viewRooms.putExtra("resInfo", b);

                    startActivity(viewRooms);
                }
                else
                {
                    Toast.makeText(ReservationActivity.this, "Please fill in valid options",Toast.LENGTH_SHORT);
                }
            }
        });

    }

    private LocalDate parseDate(int year, int month, int date)
    {
        return LocalDate.of(year, month, date);
    }

    public ResInfo getData()
    {
        return info;
    }

}
