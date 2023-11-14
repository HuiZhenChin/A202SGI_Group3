package com.example.busybuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busybuddy.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// Show Notifications for Create New Task and Due Date Reminder
public class ShowNotification extends AppCompatActivity {

    private DBManager dbManager;
    private DB db;
    RecyclerView recyclerView;
    AdapterView adapterView;
    int userID;
    List<Item> items = new ArrayList<Item>();
    String usernameValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        // get user ID and username value
        Intent intent = getIntent();
        userID = intent.getIntExtra("id", 0);
        usernameValue = intent.getStringExtra("usernameValue");

        // open the database
        dbManager = new DBManager(this);
        dbManager.open();
        db = new DB(this);

        // set up the recycler view
        recyclerView = findViewById(R.id.notification_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapterView = new AdapterView(this, items);
        recyclerView.setAdapter(adapterView);

        // fetch notifications
        Cursor cursor = dbManager.fetchActivity(userID);

        // fetch list of tasks by user
        List<TaskItem> tasks = db.getTasksByUser(userID);

        // iterate through notifications and add "created" messages to the list
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String message = cursor.getString(cursor.getColumnIndex(DB.MESSAGE));

                // Check if the message contains "created"
                if (message.toLowerCase().contains("created")) {
                    // Create an Item object and add it to the list
                    items.add(new Item(message));
                }
            }
        }

        // iterate through tasks and add items based on due date (due within 1 week)
        for (TaskItem taskItem : tasks) {
            String taskTitle = taskItem.getTitle();
            String taskDate = taskItem.getDueDate();

            // check if the notification is due within one week
            if (isDueInOneWeek(taskDate)) {
                String message = "Your task titled " + taskTitle + " due on " + taskDate + ".";

                // check if a similar message has been inserted before
                if (!db.isMessageExist(message, userID)) {
                    dbManager.ActivityLog(message, userID, taskDate);

                }items.add(new Item(message));
            }
        }
        adapterView.notifyDataSetChanged();

    // return to MainActivity to access the navigation drawer
    ImageButton menubutton = findViewById(R.id.menuBtn);
        menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMain();
            }
        });

        // mark as read button
        Button markAsRead = findViewById(R.id.button);
        markAsRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbManager.fetchUnreadActivity(userID);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        @SuppressLint("Range")  int notificationId = cursor.getInt(cursor.getColumnIndex(DB.MESSAGE_ID));
                        dbManager.updateRead(notificationId);
                        // mark all notifications as read
                        Toast.makeText(getApplicationContext(), "Marked All as Read.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void goMain(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("usernameValue", usernameValue);
        startActivity(intent);
    }

    // method to filter task due in one week
    private boolean isDueInOneWeek(String taskDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Date dueDate = sdf.parse(taskDate);

            // calculate one week before the due date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dueDate);
            calendar.add(Calendar.DAY_OF_YEAR, -7);

            // check if the current date is after one week before the due date
            return Calendar.getInstance().getTime().after(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }




}