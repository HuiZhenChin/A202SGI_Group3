package com.example.project;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Notification extends RecyclerView.ViewHolder{
    TextView notif_msg;

    public Notification(@NonNull View itemview){
        super(itemview);
        notif_msg = itemview.findViewById(R.id.notificationview);
    }
}