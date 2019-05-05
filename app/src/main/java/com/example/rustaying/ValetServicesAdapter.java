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

public class ValetServicesAdapter extends RecyclerView.Adapter<ValetServicesAdapter.ViewHolder> {
    private static final String TAG = "ValetServicesAdapter";

    private ArrayList<Service> serviceList;
    private Context mContext;

    public ValetServicesAdapter (Context mContext, ArrayList<Service> serviceList){
        this.serviceList = serviceList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_valet_services,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i){
        Log.d(TAG, "onBindViewHolder: Called");

        Service info = serviceList.get(i);

        viewHolder.requestTypeV.setText(info.getRequestType());
        viewHolder.requestTimeV.setText(info.getRequestedTimeValet());
        viewHolder.requestDateV.setText(info.getRequestDate());
        viewHolder.startingStreetV.setText(info.getStartingStreet());
        viewHolder.startingCityStateZipV.setText(info.getStartingCityStateZip());
        viewHolder.destinationStreetV.setText(info.getDestinationStreet());
        viewHolder.destinationCityStateZipV.setText(info.getDestinationCityStateZip());
        viewHolder.travelNumber.setText(info.getNumberTraveling());
    }

    @Override
    public int getItemCount(){
        return serviceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView requestTypeV, requestDateV, requestTimeV, startingStreetV, startingCityStateZipV,
                destinationStreetV, destinationCityStateZipV, travelNumber;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            requestTypeV = itemView.findViewById(R.id.requestTypeV);
            requestDateV = itemView.findViewById(R.id.requestDateV);
            requestTimeV = itemView.findViewById(R.id.requestTimeV);
            startingStreetV = itemView.findViewById(R.id.StartingStreetV);
            startingCityStateZipV = itemView.findViewById(R.id.startingCityStateZipV);
            destinationStreetV = itemView.findViewById(R.id.destinationStreetV);
            destinationCityStateZipV = itemView.findViewById(R.id.destinationCityStateZipV);
            travelNumber=itemView.findViewById(R.id.travelNumber);
        }
    }
}