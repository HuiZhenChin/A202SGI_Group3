package com.example.busybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity
{
    private DBManager dbManager;
    EventAdapter eventAdapter;
    private DB db;
    private EditText eventName;
    private TextView eventDate, eventTime;
    private int userID;
    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // open the database
        dbManager = new DBManager(this);
        db = new DB(this);
        dbManager.open();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();

        // get the username value
        Intent intentLogin = getIntent();
        String usernameValue = intentLogin.getStringExtra("usernameValue");

        // if username is not null, find the user ID
        if (usernameValue != null) {
            userID = dbManager.getUserID(usernameValue);
        }

        // get the current time and set as the event time
        time = LocalTime.now();
        // get the selected date and set as the event date
        eventDate.setText(CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTime.setText(CalendarUtils.formattedTime(time));

    }

    private void initWidgets()
    {
        eventName = findViewById(R.id.eventNameET);
        eventDate = findViewById(R.id.eventDateTV);
        eventTime = findViewById(R.id.eventTimeTV);
    }

    // save event to the selected date recycler view
    public void saveEventAction(View view)
    {
        String eventTitle = eventName.getText().toString();  // get the event title string
        Event newEvent = new Event(eventTitle, CalendarUtils.selectedDate, time);  // create object for new event
        Event.eventsList.add(newEvent);  // add new event to the event list
        finish();

        String name = eventName.getText().toString();  // get the event title string
        String date = eventDate.getText().toString(); // get the event date string
        String time= eventTime.getText().toString(); // get the event created time string
        // add new event details to the Calendar table
        dbManager.calendarEvent(name, date, time, userID);
    }


}
