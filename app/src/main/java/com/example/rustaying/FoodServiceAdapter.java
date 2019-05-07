/************************
 Authors:
 Eric Zhang
 Thomas Tran
 Rameen Masood
 *************************/

package com.example.rustaying;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FoodServiceAdapter extends RecyclerView.Adapter<FoodServiceAdapter.ViewHolder> {
    private static final String TAG = "FoodServicesAdapter";

    private ArrayList<Service> serviceList;
    private Context mContext;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    public FoodServiceAdapter(Context mContext, ArrayList<Service> serviceList){
        this.serviceList = serviceList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_food,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i){
        Log.d(TAG, "onBindViewHolder: Called");
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        final Service info = serviceList.get(i);

        viewHolder.requestType.setText(info.getRequestType());
        viewHolder.requestDate.setText(info.getRequestDate());
        viewHolder.requestTime.setText(info.getRequestedTimeFoodService());
        viewHolder.foodPrice.setText(info.getFoodPrice());
        viewHolder.gardenSalad.setText(info.getGardenSalad());
        viewHolder.tomatoSoup.setText(info.getTomatoSoup());
        viewHolder.friedChicken.setText(info.getFriedChicken());
        viewHolder.cheesePizza.setText(info.getCheesePizza());
        viewHolder.spaghetti.setText(info.getSpaghetti());
        viewHolder.macAndCheese.setText(info.getMacAndCheese());
        viewHolder.vanillaIceCream.setText(info.getVanillaIceCream());
        viewHolder.fruitCake.setText(info.getFruitCake());
        viewHolder.coke.setText(info.getCoke());
        viewHolder.sprite.setText(info.getSprite());
        viewHolder.appleJuice.setText(info.getAppleJuice());
        viewHolder.input.setText(info.getInputs());
        viewHolder.idF.setText(String.valueOf(info.getId()));
        viewHolder.statusF.setText(info.getStatus());



        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setMessage("Set status of Service:").setPositiveButton("Completed",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase.getInstance().getReference().child("Service")
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                Map<String, Object> statusUpdate = new HashMap<>();
                                                long currentId=info.getId();
                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    Log.d(TAG, "ViewServiceClass: =============================" + snapshot.getKey());
                                                    String userId = snapshot.getKey();
                                                    for (DataSnapshot snapshot2 : snapshot.getChildren()){
                                                        String requestID=snapshot2.getKey();
                                                        Log.d(TAG, "ViewServiceClass: =============================" + snapshot2.getKey());
                                                        if (snapshot2.child("id").getValue()!=null) {
                                                            long id = Integer.parseInt(snapshot2.child("id").getValue().toString());
                                                            if (id==currentId){
                                                                statusUpdate.put("status","Completed");
                                                                myRef.child("Service").child(userId).child(requestID).updateChildren(statusUpdate);
                                                                viewHolder.statusF.setText(info.getStatus());
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });

                            }
                        }).setNegativeButton("In Progress  ",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase.getInstance().getReference().child("Service")
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                Map<String, Object> statusUpdate = new HashMap<>();
                                                long currentId=info.getId();
                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    String userId = snapshot.getKey();
                                                    for (DataSnapshot snapshot2 : snapshot.getChildren()){
                                                        String requestID=snapshot2.getKey();
                                                        if (snapshot2.child("id").getValue()!=null) {
                                                            long id = Integer.parseInt(snapshot2.child("id").getValue().toString());
                                                            if (id==currentId){
                                                                statusUpdate.put("status","In Progress");
                                                                myRef.child("Service").child(userId).child(requestID).updateChildren(statusUpdate);
                                                                viewHolder.statusF.setText(info.getStatus());
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });
                            }
                        });

                AlertDialog alert = alertDialog.create();
                alert.setTitle("Status Update");
                alert.show();

                Button negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                negativeButton.setTextColor(Color.RED);
                negativeButton.setBackgroundColor(Color.WHITE);

                Button positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setTextColor(Color.GREEN);
                positiveButton.setBackgroundColor(Color.WHITE);
            }
        });
    }

    @Override
    public int getItemCount(){
        return serviceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView card;
        TextView requestType, requestDate, requestTime,  foodPrice, gardenSalad, tomatoSoup,
                friedChicken, cheesePizza, spaghetti, macAndCheese, vanillaIceCream,
                fruitCake, coke, sprite, appleJuice, input ,statusF, idF;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            card = itemView.findViewById(R.id.FoodServicesCard);
            requestType = itemView.findViewById(R.id.requestTypeF);
            requestDate = itemView.findViewById(R.id.requestDateF);
            requestTime = itemView.findViewById(R.id.requestTimeF);
            foodPrice = itemView.findViewById(R.id.foodtotal);
            gardenSalad= itemView.findViewById(R.id.numbGardenSalad);
            tomatoSoup=itemView.findViewById(R.id.numbTomatoSoup);
            friedChicken=itemView.findViewById(R.id.numbFriedChicken);
            cheesePizza=itemView.findViewById(R.id.numbCheesePizza);
            spaghetti=itemView.findViewById(R.id.numbSpaghetti);
            macAndCheese=itemView.findViewById(R.id.numbMacAndCheese);
            vanillaIceCream=itemView.findViewById(R.id.numbVanillaIceCream);
            fruitCake=itemView.findViewById(R.id.numbFruitCake);
            coke=itemView.findViewById(R.id.numbCoke);
            sprite=itemView.findViewById(R.id.numbSprite);
            appleJuice=itemView.findViewById(R.id.numbAppleJuice);
            input = itemView.findViewById(R.id.inputsFood);
            idF=itemView.findViewById(R.id.idF);
            statusF = itemView.findViewById(R.id.statusF);

        }
    }
}