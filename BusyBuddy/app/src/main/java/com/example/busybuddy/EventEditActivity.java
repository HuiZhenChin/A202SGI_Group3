package com.example.busybuddy;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventName;
    private TextView eventDate, eventTime;

    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        eventDate.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTime.setText("Time: " + CalendarUtils.formattedTime(time));
    }

    private void initWidgets()
    {
        eventName = findViewById(R.id.eventNameET);
        eventDate = findViewById(R.id.eventDateTV);
        eventTime = findViewById(R.id.eventTimeTV);
    }

    public void saveEventAction(View view)
    {
        String eventTitle = eventName.getText().toString();
        Event newEvent = new Event(eventTitle, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
        finish();
    }
}