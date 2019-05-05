package com.example.rustaying;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class InboxActivityAdapter extends RecyclerView.Adapter<InboxActivityAdapter.ViewHolder> {
    private static final String TAG = "InboxActivityAdapter";
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private ArrayList<Service> serviceList;
    Service serv = new Service();
    private Context mContext;

    public InboxActivityAdapter (Context mContext, ArrayList<Service> serviceList){
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
        viewHolder.status.setText(info.getStatus());
    }

    @Override
    public int getItemCount(){
        return serviceList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView card;
        TextView requestType, status;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            card = itemView.findViewById(R.id.)
            requestType = itemView.findViewById(R.id.requestTypeB);
            status= itemView.findViewById((R.id.status));
        }
    }
}