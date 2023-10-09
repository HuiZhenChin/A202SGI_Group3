package com.example.project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShowNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        RecyclerView recyclerView = findViewById(R.id.notificationview);

        List<Item> items = new ArrayList<Item>();
        items.add(new Item("Test1"));
        items.add(new Item("Test2"));
        items.add(new Item("Test3"));
        items.add(new Item("Test4"));
        items.add(new Item("Test5"));
        items.add(new Item("Test6"));
        items.add(new Item("Test7"));
        items.add(new Item("Test8"));
        items.add(new Item("Test9"));
        items.add(new Item("Test10"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdaptView(getApplicationContext(),items));
    }
}
