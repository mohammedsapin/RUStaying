package com.example.rustaying;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class newRoomAdapter extends RecyclerView.Adapter<newRoomAdapter.RoomViewHolder>{

    private static final String TAG = "newRoomAdapter";
    private Context mCtx;
    private ArrayList<Room> roomList;

    public newRoomAdapter(Context mCtx, ArrayList<Room> roomList) {
        this.mCtx = mCtx;
        this.roomList = roomList;
    }


    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //LayoutInflater inflater = LayoutInflater.from(mCtx);
        //View view = inflater.inflate(R.layout.new_rooms, null);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_rooms, viewGroup, false);
        //View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);

        RoomViewHolder holder = new RoomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder roomViewHolder, int i) {
        Room room = roomList.get(i);
        Log.i(TAG, room.getRoomType());

        roomViewHolder.roomType.setText(room.getRoomType());
        roomViewHolder.roomNum.setText(room.getRoomId());
        roomViewHolder.price.setText("$250");

    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    class RoomViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView roomType, roomNum, price;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.roomPic);
            roomType = itemView.findViewById(R.id.roomType);
            roomNum = itemView.findViewById(R.id.roomNum);
            price = itemView.findViewById(R.id.price);

        }
    }
}
