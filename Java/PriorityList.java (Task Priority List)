package com.example.project;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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

    String progressitems[] = {"In Progress", "Completed"};

    ArrayList list= new ArrayList(Arrays.asList(progressitems));

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

        //go back create task

        ImageButton add= findViewById(R.id.addBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });

        //progress dropdown list
        Spinner progressSpinner = findViewById(R.id.taskProgress);
        ArrayAdapter adapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        progressSpinner.setAdapter(adapter);

        progressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //when an item is selected
                if (position ==0) {

                    //in progress

                } else if (position == list.size() - 1) {

                    //completed

                    };

            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //pass
            }
        });


        // Get the values from the Intent
        Intent intent = getIntent();
        String taskTitle = intent.getStringExtra("taskTitle");
        String selectedDate = intent.getStringExtra("selectedDate");

        // Display the values in TextViews
        TextView titleTextView = findViewById(R.id.taskTitle);
        TextView dateTextView = findViewById(R.id.taskDue);
        Spinner progress= findViewById(R.id.taskProgress);

        titleTextView.setText( taskTitle);
        dateTextView.setText(selectedDate);

        titleTextView.setVisibility(View.VISIBLE);
        dateTextView.setVisibility(View.VISIBLE);
        progress.setVisibility(View.VISIBLE);
        //solve spinner hide problem
        //hide priority type according to radio grp

    }

    public void openNewActivity(){
        Intent intent = new Intent(this, NewActivity.class);
        startActivity(intent);
    }
}
