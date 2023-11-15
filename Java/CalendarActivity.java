package com.example.busybuddy;

import static com.example.busybuddy.CalendarUtils.daysMonthArray;
import static com.example.busybuddy.CalendarUtils.monthYearFromDate;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

// Calendar Activity (Display Calendar)
public class CalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    String usernameValue, passwordValue= "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        initWidgets();

        // get username value
        Intent intentLogin = getIntent();
        usernameValue = intentLogin.getStringExtra("usernameValue");
        passwordValue = intentLogin.getStringExtra("passwordValue");

        // set monthly view
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

        // info icon guidelines
        ImageButton questionMarkButton = findViewById(R.id.questionButton);
        questionMarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGuidelinesDialog();
            }
        });

        // go back to MainActivity to access Navigation Drawer
        ImageButton menubutton = findViewById(R.id.menuBtn);
        menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMain();
            }
        });



    }

    // return to MainActivity
    public void goMain(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("usernameValue", usernameValue);
        intent.putExtra("passwordValue", passwordValue);
        startActivity(intent);
    }
    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    // set monthly view format in recycler view
    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    // previous month
    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    // next month
    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    // select month
    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    // pass value to WeekViewActivity
    public void weeklyAction(View view)
    {
        Intent intentLogin = new Intent(this, WeekViewActivity.class);
        intentLogin.putExtra("usernameValue", usernameValue);
        intentLogin.putExtra("passwordValue", passwordValue);
        startActivity(intentLogin);
    }

    // calendar guidelines
    private void showGuidelinesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Calendar Guidelines");
        builder.setMessage("Hi buddies, you may start creating an urgent/ long-term task now.\n\n" +
                "Here are some steps to create the task.\n" +
                "1. Select the date that you wish to create a new task. At the same time, you can switch the month by pressing the" +
                " < and > buttons \n" +
                "2. After selecting the date, press the Weekly button and you will be directed to a new page.\n" +
                "3. Press the Create New Task button. \n" +
                "4. Enter the title of the task.\n" +
                " \n" +
                "Note: Your task cannot be deleted once it is created. \n" +
                " \n" +
                " Enjoy your day! \n"
        );
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}