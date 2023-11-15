package com.example.busybuddy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// calendar date, month and year formatting
public class CalendarUtils
{
    public static LocalDate selectedDate;

    // date format
    public static String formattedDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(formatter);
    }

    // time format
    public static String formattedTime(LocalTime time)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        return time.format(formatter);
    }

    // month with year format
    public static String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public static ArrayList<LocalDate> daysMonthArray(LocalDate date) {
        ArrayList<LocalDate> daysMonthArray = new ArrayList<>();

        // check if selectedDate is not null before using it
        if (selectedDate == null) {

            return daysMonthArray;
        }

        YearMonth yearMonth = YearMonth.from(date);

        int daysMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1); // Use selectedDate directly
        int daysWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {
            if (i <= daysWeek || i > daysMonth + daysWeek)
                daysMonthArray.add(null);
            else
                daysMonthArray.add(LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), i - daysWeek));
        }
        return daysMonthArray;
    }

    public static ArrayList<LocalDate> daysWeekArray(LocalDate selectedDate)
    {
        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate current = sundayForDate(selectedDate);
        LocalDate endDate = current.plusWeeks(1);

        while (current.isBefore(endDate))
        {
            days.add(current);
            current = current.plusDays(1);
        }
        return days;
    }

    // sunday format
    private static LocalDate sundayForDate(LocalDate current)
    {
        LocalDate oneWeekPrevious = current.minusWeeks(1);

        while (current.isAfter(oneWeekPrevious))
        {
            if(current.getDayOfWeek() == DayOfWeek.SUNDAY)
                return current;

            current = current.minusDays(1);
        }

        return null;
    }


}
