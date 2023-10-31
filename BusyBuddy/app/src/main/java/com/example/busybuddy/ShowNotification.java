package com.example.busybuddy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busybuddy.R;

import java.util.ArrayList;
import java.util.List;

public class ShowNotification extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterView adapterView;
    List<Item> items = new ArrayList<Item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        recyclerView= findViewById(R.id.notification_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        items.add(new Item("You created new task named Data Science"));
        items.add(new Item("Your task Android App due 2 days later!"));
        items.add(new Item("You deleted a task named Buy Pencil"));
        items.add(new Item("Test4"));
        items.add(new Item("Test5"));
        items.add(new Item("Test6"));
        items.add(new Item("Test7"));
        items.add(new Item("Test8"));
        items.add(new Item("Test9"));
        items.add(new Item("Test10"));

        adapterView = new AdapterView(this, items);

        recyclerView.setAdapter(adapterView);
    }
}