package com.example.rustaying;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewServices extends AppCompatActivity implements SwipeControllerListener {

    private static final String TAG = "ViewServices";

    //Services List
    private ArrayList<Service> serviceList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ViewServicesAdapter adapter;


    //Firebase
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_services);

        createRecycleView();

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged: Signed In");
                } else {
                    Log.d(TAG, "onAuthStateChanged: Signed out");
                }
            }
        };

        myRef.child("Service").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                showData(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                showData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void createRecycleView(){
     Log.d(TAG, "createRecycleView: Started view");
     RecyclerView recyclerView = findViewById(R.id.viewServicesRecycleView);
     recyclerView.setLayoutManager(new LinearLayoutManager(this));
     ViewServicesAdapter adapter = new ViewServicesAdapter(this,serviceList);
     recyclerView.setItemAnimator(new DefaultItemAnimator());
     recyclerView.setAdapter(adapter);

     ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new SwipeController(0,
             ItemTouchHelper.LEFT,this);

     new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }


    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()) {

            Service info = new Service();

            //bellboy
            info.setRequestType(data.getValue(Service.class).getRequestType());
            info.setRequestDate(data.getValue(Service.class).getRequestDate());
            info.setLuggageValue(data.getValue(Service.class).getLuggageValue());
            info.setRequestedTimeBellboy(data.getValue(Service.class).getRequestedTimeBellboy());
            info.setFromWhere(data.getValue(Service.class).getFromWhere());

            //Valet
            info.setRequestedTimeValet(data.getValue(Service.class).getRequestedTimeValet());
            info.setAnswer1(data.getValue(Service.class).getAnswer1());
            info.setAnswer2(data.getValue(Service.class).getAnswer2());
            info.setAnswer3(data.getValue(Service.class).getAnswer3());
            info.setAnswer4(data.getValue(Service.class).getAnswer4());

            //Maintenance
            info.setRequestedTimeMaintenance(data.getValue(Service.class).getRequestedTimeMaintenance());
            info.setInputs(data.getValue(Service.class).getInputs());
            info.setBathroom(data.getValue(Service.class).getBathroom());
            info.setElectronic(data.getValue(Service.class).getElectronic());
            info.setLighting(data.getValue(Service.class).getLighting());
            info.setCheckboxes(data.getValue(Service.class).getCheckboxes());

            //Room Service
            info.setRequestedTimeRoomService(data.getValue(Service.class).getRequestedTimeRoomService());
            info.setTowels(data.getValue(Service.class).getTowels());
            info.setSoap(data.getValue(Service.class).getSoap());
            info.setBedsheets(data.getValue(Service.class).getBedsheets());
            info.setCleaningservice(data.getValue(Service.class).getBedsheets());


            //bellboy
            serviceList.add(new Service(info.getRequestType(),info.getRequestDate(),
                    info.getLuggageValue(),info.getRequestedTimeBellboy(),info.getFromWhere()));

            //valet (Mandy edited)
            serviceList.add(new Service(info.getRequestType(),info.getRequestDate(),
                    info.getRequestedTimeValet(),info.getStartingStreet(),info.getStartingCityStateZip(),
                    info.getDestinationStreet(),info.getDestinationCityStateZip()));

            //Maintenance
            serviceList.add(new Service(info.getRequestType(),info.getRequestDate(),
                    info.getRequestedTimeMaintenance(),info.getInputs(),info.getBathroom(),
                    info.getElectronic(),info.getLighting(),info.getCheckboxes()));

            //Room Service
            serviceList.add(new Service(info.getRequestType(),info.getRequestDate(),
                    info.getRequestedTimeRoomService(), info.getInputs(), info.getTowels(),
                    info.getSoap(),info.getBedsheets(),info.getCleaningservice(),
                    info.getCheckboxes()));

            //add array list to recycle view
            createRecycleView();
        }
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


    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ViewServicesAdapter.ViewHolder){
            int deletedIndex = viewHolder.getAdapterPosition();
            adapter.removeItem(deletedIndex);
            Toast.makeText(ViewServices.this, "Completed Request",Toast.LENGTH_SHORT).show();
        }
    }
}
