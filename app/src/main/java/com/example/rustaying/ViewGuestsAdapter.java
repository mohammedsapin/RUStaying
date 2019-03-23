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

public class ViewGuestsAdapter extends RecyclerView.Adapter<ViewGuestsAdapter.ViewHolder> {

    private static final String TAG = "ViewGuestsAdapter";

    private ArrayList<Guest> guestInfo;
    private Context mContext;

    public ViewGuestsAdapter (Context mContext, ArrayList<Guest> guestInfo){
        this.guestInfo = guestInfo;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_guests,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: Called");

        Guest info = guestInfo.get(i);

        viewHolder.firstName.setText(info.getFirstName());
        viewHolder.lastName.setText(info.getLastName());
        viewHolder.email.setText(info.getGuestEmail());
    }

    @Override
    public int getItemCount() {
        return guestInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView firstName, lastName, email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.first_name);
            lastName = itemView.findViewById(R.id.last_name);
            email = itemView.findViewById(R.id.guest_email);
        }
    }
}
