/************************
 Authors:
 Mohammed Sapin
 Nga Man (Mandy) Cheng
 Purna Haque
 *************************/

package com.example.rustaying;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HotelData extends AppCompatActivity {

    private static final String TAG = "HotelData";

    BarChart chart;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    List<BarEntry> entries1 = new ArrayList<>();
    List<BarEntry> entries2 = new ArrayList<>();
    List<BarEntry> entries3 = new ArrayList<>();
    List<BarEntry> entries4 = new ArrayList<>();
    List<BarEntry> entries5 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_data);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.d(TAG, "onAuthStateChanged: Signed In");
                }else{
                    Log.d(TAG, "onAuthStateChanged: Signed out");
                }
            }
        };

        myRef.child("Feedback").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showData(DataSnapshot dataSnapshot){

        ArrayList<Double> ratingOneData = new ArrayList<>();
        ArrayList<Double> ratingTwoData = new ArrayList<>();
        ArrayList<Double> ratingThreeData = new ArrayList<>();
        ArrayList<Double> ratingFourData = new ArrayList<>();
        ArrayList<Double> ratingFiveData = new ArrayList<>();

        double data1;
        double data2;
        double data3;
        double data4;
        double data5;

        for (DataSnapshot data : dataSnapshot.getChildren()){
            data1 = Double.parseDouble((String)data.child("rating1").getValue());
            data2 = Double.parseDouble((String)data.child("rating2").getValue());
            data3 = Double.parseDouble((String)data.child("rating3").getValue());
            data4 = Double.parseDouble((String)data.child("rating4").getValue());
            data5 = Double.parseDouble((String)data.child("rating5").getValue());

            ratingOneData.add(data1);
            ratingTwoData.add(data2);
            ratingThreeData.add(data3);
            ratingFourData.add(data4);
            ratingFiveData.add(data5);
        }

        double ratingOneAvg = average(ratingOneData);
        double ratingTwoAvg = average(ratingTwoData);
        double ratingThreeAvg = average(ratingThreeData);
        double ratingFourAvg = average(ratingFourData);
        double ratingFiveAvg = average(ratingFiveData);

        entries1.add(new BarEntry(0, (float) ratingOneAvg));
        entries2.add(new BarEntry(1, (float) ratingTwoAvg));
        entries3.add(new BarEntry(2, (float) ratingThreeAvg));
        entries4.add(new BarEntry(3, (float) ratingFourAvg));
        entries5.add(new BarEntry(4, (float) ratingFiveAvg));

        BarDataSet set1 = new BarDataSet(entries1,"Overall Experience");
        BarDataSet set2 = new BarDataSet(entries2,"Room Service Rating");
        BarDataSet set3 = new BarDataSet(entries3,"Bellboy Service Rating");
        BarDataSet set4 = new BarDataSet(entries4,"Valet Service Rating");
        BarDataSet set5 = new BarDataSet(entries5,"Maintenance Service Rating");

        //set font size
        set1.setValueTextSize(16f);
        set2.setValueTextSize(16f);
        set3.setValueTextSize(16f);
        set4.setValueTextSize(16f);
        set5.setValueTextSize(16f);

        // sets colors for the dataset.
        set1.setColors(ColorTemplate.rgb("#FF0000"));
        set2.setColors(ColorTemplate.rgb("#0000FF"));
        set3.setColors(ColorTemplate.rgb("#00FF00"));
        set4.setColors(ColorTemplate.rgb("#FF00FF"));
        set5.setColors(ColorTemplate.rgb("#FFFF00"));

        //chart
        chart = (BarChart) findViewById(R.id.barChart);
        BarData graphData = new BarData(set1,set2,set3,set4,set5);
        chart.setDrawGridBackground(false); //remove grid
        chart.setDrawBarShadow(false); //remove shadow
        chart.setDrawValueAboveBar(true); //show numbers above bar
        chart.setPinchZoom(false); //remove zoom
        chart.setScaleEnabled(false); //remove scaling
        chart.setFitBars(true); // make the x-axis fit exactly all bars
        chart.fitScreen();

        //X-axis
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setEnabled(false);

        //Y-Axis
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisLeft().setAxisMaximum(5);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setWordWrapEnabled(true);

        //Attach data
        chart.setData(graphData);
        chart.notifyDataSetChanged();
        chart.invalidate();
    }

    private double average(ArrayList<Double> List){
        double total = 0;
        double avg = 0;
        for (int i = 0; i < List.size(); i++){
            total += List.get(i);
            avg = total/List.size();
        }
        return avg;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
