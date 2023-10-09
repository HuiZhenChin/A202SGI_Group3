package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptView extends RecyclerView.Adapter<Notification> {

    Context context;
    List<Item> items;

    public AdaptView(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Notification onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Notification(LayoutInflater.from(context).inflate(R.layout.notif_view,parent,false));
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
