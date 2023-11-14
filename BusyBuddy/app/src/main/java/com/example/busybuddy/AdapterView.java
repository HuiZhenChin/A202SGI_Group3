package com.example.busybuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busybuddy.R;

import java.util.List;

// Notifications Recycler View Adapter
public class AdapterView extends RecyclerView.Adapter<Notification> {

    Context context;
    List<Item> items;

    public AdapterView(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    // customized row design for recycler view
    @NonNull
    @Override
    public Notification onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Notification(LayoutInflater.from(context).inflate(R.layout.notification_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Notification holder, int position) {
        holder.notif_msg.setText(items.get(position).getNotification());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}