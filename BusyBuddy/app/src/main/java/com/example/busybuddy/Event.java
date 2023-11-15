package com.example.busybuddy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

// Event class (Calendar Event)
public class Event
{
    public static ArrayList<Event> eventsList = new ArrayList<>();

    // add event to the list
    public static ArrayList<Event> eventsForDate(LocalDate date)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            if(event.getDate().equals(date))
                events.add(event);
        }

        return events;
    }


    private String name;
    private LocalDate date;
    private LocalTime time;

    // check whether the list has event or not
    public static boolean hasEventsForDate(LocalDate date) {
        if (date == null) {
            return false; // or handle the case where date is null
        }

        for (Event event : eventsList) {
            LocalDate eventDate = event.getDate();
            if (eventDate != null && eventDate.equals(date)) {
                return true;
            }
        }
        return false;
    }


    public Event(String name, LocalDate date, LocalTime time)
    {
        this.name = name;  // event title
        this.date = date;  // event date
        this.time = time;  // event created time
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }
}
