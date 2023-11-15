package com.example.busybuddy;

import static com.example.busybuddy.CalendarUtils.daysWeekArray;
import static com.example.busybuddy.CalendarUtils.monthYearFromDate;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// Display calendar in weekly format
public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    String usernameValue, passwordValue= "";
    EventAdapter eventAdapter;
    private DB db;
    private DBManager dbManager;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();

        // open the database
        dbManager = new DBManager(this);
        db = new DB(this);
        dbManager.open();

        // set up the event adapter
        eventAdapter = new EventAdapter(this, Event.eventsList);

        // get username value and find the user ID
        Intent intentLogin = getIntent();
        usernameValue = intentLogin.getStringExtra("usernameValue");
        passwordValue = intentLogin.getStringExtra("passwordValue");
        if (usernameValue != null) {
            userID = dbManager.getUserID(usernameValue);
        }

        // set up the week view
        setWeekView();

        // if user ID is found, display the calendar event
        if (userID != 0) {
            Cursor cursor = dbManager.displayCalendarEvent(userID);
            LocalDate date = null;

            if (cursor != null && cursor.getCount() > 0) {
                try {
                    int columnIndexEventName = cursor.getColumnIndex(DB.EVENT);
                    int columnIndexDate = cursor.getColumnIndex(DB.DATE);
                    int columnIndexTime = cursor.getColumnIndex(DB.TIME);

                    if (cursor.getCount() == 0) {
                        // no data to display
                    } else {
                        // move the cursor to the first position
                        cursor.moveToFirst();
                    }

                    do {
                        // retrieve column indices only once
                        // avoid duplication of data
                        if (columnIndexEventName != -1 && columnIndexDate != -1 && columnIndexTime != -1) {

                            String eventName = cursor.getString(columnIndexEventName);
                            String dateString = cursor.getString(columnIndexDate);
                            String timeString = cursor.getString(columnIndexTime);
                            DateTimeFormatter dateFormatter;
                            LocalTime time;

                            if (timeString == null) {
                                time = LocalTime.now();  // set the current time
                            } else {
                                if (dateString.contains("-")) {
                                    // use the first formatter if the date string contains "-"
                                    dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                } else {
                                    // otherwise, use the second formatter
                                    dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
                                }

                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm:ss a");

                                date = LocalDate.parse(dateString, dateFormatter);  // assign the date
                                time = LocalTime.parse(timeString, timeFormatter);  // assign the time
                            }

                            // check if the event is already in the list
                            boolean eventExists = false;
                            for (Event event : Event.eventsList) {
                                if (event.getName().equals(eventName)) {
                                    eventExists = true;
                                    break;
                                }
                            }

                            if (!eventExists) {
                                // create an Event object and add it to the adapter
                                Event newEvent = new Event(eventName, date, time);
                                Event.eventsList.add(newEvent);

                            }
                        } else {
                            // handle the case where one or more columns are not found
                        }
                    } while (cursor.moveToNext());

                } finally {
                    eventAdapter.notifyDataSetChanged();
                    cursor.close(); // close the cursor
                }
            } else {
                // handle the case where there are no rows in the cursor
            }
        }
    }


    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);

    }

    // set up weekly format in recycler view
    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdapter();
    }

    // previous week
    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    // next week
    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    // pass intent while user wants to create an event in calendar
    public void newEventAction(View view)
    {
        Intent intentLogin = new Intent(this, EventEditActivity.class);
        intentLogin.putExtra("usernameValue", usernameValue);
        intentLogin.putExtra("passwordValue", passwordValue);
        startActivity(intentLogin);
    }

    // pass intent while user wants to return to main calendar page
    public void monthlyAction(View view)
    {
        Intent intentLogin = new Intent(this, CalendarActivity.class);
        intentLogin.putExtra("usernameValue", usernameValue);
        intentLogin.putExtra("passwordValue", passwordValue);
        startActivity(intentLogin);
    }
}
