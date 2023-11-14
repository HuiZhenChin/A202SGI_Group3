package com.example.busybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busybuddy.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// display each folder's task (Folder Page)
public class DisplayFolder extends AppCompatActivity {

    private RecyclerView recyclerView;  // display in recycler view
    private FolderAdapter adapter;  // customized folder adapter
    private DBManager dbManager;
    private int userID;
    String usernameValue ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_folder);

        // open the db Manager
        dbManager = new DBManager(this);
        dbManager.open();

        // get intent for username
        Intent intentLogin = getIntent();
        usernameValue = intentLogin.getStringExtra("usernameValue");
        // get the current user ID based on username
        userID = dbManager.getUserID(usernameValue);

        recyclerView = findViewById(R.id.recyclerView);

        // get the list of tasks for the current user
        List<TaskItem> taskItems = getFolderItems();

        // sorting function for comparing and hiding the task folder name (for those having the same folder)
        // only display folder name for the first task
        // allow user to have a quick view of tasks in each stored folder
        // example format:
        /* Folder Name 1         Task 1
                                 Task 2
                                 Task 3
           Folder Name 2         Task 4
           Folder Name 3         Task 5
                                 Task 6
        */
        Collections.sort(taskItems, new Comparator<TaskItem>() {
            @Override
            public int compare(TaskItem taskItem1, TaskItem taskItem2) {
                return taskItem1.getFolder().compareTo(taskItem2.getFolder());
            }
        });

        // create and set the adapter
        adapter = new FolderAdapter(this, taskItems); // call the Folder Adapter
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // set up the layout

        // back button for going back to the main page to access the navigation drawer
        ImageButton backbutton = findViewById(R.id.backBtn);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMain();
            }
        });

    }

    // access and call the getTasksByUserId function in db
    private List<TaskItem> getFolderItems() {
        // Assuming you have a DB instance. Adjust the following line accordingly.
        DB db = new DB(this);
        return db.getTasksByUserId(userID);
    }

    // return to main page
    public void goMain(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("usernameValue", usernameValue);
        startActivity(intent);
    }
}


