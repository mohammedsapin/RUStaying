package com.example.rustaying;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodServiceAdapter extends RecyclerView.Adapter<FoodServiceAdapter.ViewHolder> {
    private static final String TAG = "FoodServicesAdapter";

    private ArrayList<Service> serviceList;
    private Context mContext;

    public FoodServiceAdapter(Context mContext, ArrayList<Service> serviceList){
        this.serviceList = serviceList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_view_food_services,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i){
        Log.d(TAG, "onBindViewHolder: Called");

        Service info = serviceList.get(i);

        viewHolder.requestType.setText(info.getRequestType());
        viewHolder.requestDate.setText(info.getRequestDate());
        viewHolder.requestTime.setText(info.getRequestedTimeFoodService());
        viewHolder.foodPrice.setText(String.valueOf(info.getFoodPrice()));
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
    }

    @Override
    public int getItemCount(){
        return serviceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView requestType, requestDate, requestTime,  foodPrice, gardenSalad, tomatoSoup,
                friedChicken, cheesePizza, spaghetti, macAndCheese, vanillaIceCream,
                fruitCake, coke, sprite, appleJuice, input;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            requestType = itemView.findViewById(R.id.requestTypeF);
            requestDate = itemView.findViewById(R.id.requestDateF);
            requestTime = itemView.findViewById(R.id.requestTimeF);
            foodPrice = itemView.findViewById(R.id.foodprice);
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
            input = itemView.findViewById(R.id.inputsR);
        }
    }
}