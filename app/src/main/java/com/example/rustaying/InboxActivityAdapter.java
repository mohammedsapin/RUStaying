/************************
 Authors:
 Keya Patel
 Zain Sayed
 *************************/

package com.example.rustaying;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InboxActivityAdapter extends RecyclerView.Adapter<InboxActivityAdapter.ViewHolder> {
    private static final String TAG = "InboxActivityAdapter";
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private String userID;
    private ArrayList<Service> serviceList;
    Service serv = new Service();
    private Context mContext;

    public InboxActivityAdapter (Context mContext, ArrayList<Service> serviceList){
        this.serviceList = serviceList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_inbox,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i){
        //Log.d(TAG, "onBindViewHolder: Called");
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        final Service info = serviceList.get(i);

        viewHolder.requestType.setText(info.getRequestType());
        viewHolder.status.setText(info.getStatus());
        viewHolder.requestDate.setText(info.getRequestDate());

        //viewHolder.id.setText(String.valueOf(info.getId()));
    }
    @Override
    public int getItemCount(){
        return serviceList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView card;
        TextView requestType, status, requestDate;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            card = itemView.findViewById(R.id.inboxCard);
            requestType = itemView.findViewById(R.id.requestTypeInbox);
            status= itemView.findViewById((R.id.statusInbox));
            requestDate=itemView.findViewById(R.id.inboxDate);
            //id=itemView.findViewById(R.id.id);
        }
    }
}