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

public class ViewServicesAdapter extends RecyclerView.Adapter<ViewServicesAdapter.ViewHolder> {
    private static final String TAG = "ViewServicesAdapter";

    private ArrayList<Service> serviceList;
    private Context mContext;

    public ViewServicesAdapter (Context mContext, ArrayList<Service> serviceList){
        this.serviceList = serviceList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_services,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i){
        Log.d(TAG, "onBindViewHolder: Called");

        Service info = serviceList.get(i);

        viewHolder.requestType.setText(info.getRequestType());
        viewHolder.luggageVal.setText(info.getLuggageValue());
        viewHolder.requestTime.setText(info.getRequestedTime());
    }

    @Override
    public int getItemCount(){
        return serviceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView requestType, luggageVal, requestTime;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            requestType = itemView.findViewById(R.id.requestType);
            luggageVal = itemView.findViewById(R.id.luggageVal);
            requestTime = itemView.findViewById(R.id.requestTime);
        }
    }
}
