package com.example.project;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PriorityList extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    List<String> taskList;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.priority_list);

        listView = findViewById(R.id.searchList);
        searchView = findViewById(R.id.searchView);

        taskList = new ArrayList<>(Arrays.asList("Data Science", "Theory of Computation", "Cybersecurity", "Quiz", "Test", "Android Development Project", "Big Data Programming Project", "Advanced Algorithm Viva", "Software Engineering Project"));
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        listView.setAdapter(arrayAdapter);

        // Set up the SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the ListView as the user types
                if (TextUtils.isEmpty(newText)) {
                    listView.setVisibility(View.INVISIBLE); // Hide ListView when search is empty
                } else {
                    listView.setVisibility(View.VISIBLE); // Show ListView when search is active
                    arrayAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });

        ImageButton add= findViewById(R.id.addBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });

    }

    public void openNewActivity(){
        Intent intent = new Intent(this, NewActivity.class);
        startActivity(intent);
    }
}