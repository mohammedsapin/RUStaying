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

public class ViewRoomsAdapter extends RecyclerView.Adapter<ViewRoomsAdapter.ViewHolder> {
    private static final String TAG = "ViewRoomsAdapter";
    private ArrayList<Room> roomList;
    private Context mContext;

    public ViewRoomsAdapter(Context mContext, ArrayList<Room> roomList) {
        this.roomList = roomList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewRoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_rooms,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewRoomsAdapter.ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: Called");

        Room info = roomList.get(i);

        String isAvailableString;
        if(info.getIsAvailable())
        {
            isAvailableString = "YES";
        }
        else
        {
            isAvailableString = "NO";
        }

        //String roomNumber = Integer.toString(info.getRoomId());
        //Log.d(TAG, "onBindViewHolder: " + roomNumber);

        viewHolder.roomID.setText("Room Number: " + info.getRoomId());
        viewHolder.roomType.setText("Room Type: " + info.getRoomType());
        viewHolder.isAvailable.setText("Available? " + isAvailableString);
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView roomID, roomType, isAvailable;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomID = itemView.findViewById(R.id.roomid);
            roomType = itemView.findViewById(R.id.roomtype);
            isAvailable = itemView.findViewById(R.id.availability);
        }
    }
}