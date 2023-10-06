package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import java.util.Calendar;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private TextView selectedDateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatePicker();

        dateButton = findViewById(R.id.dateInput);
        selectedDateView = findViewById(R.id.selectedDate);

        //press to select date
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
    }

    private void initDatePicker() {
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            month = month + 1;
            String date = makeDateString(day, month, year);
            selectedDateView.setText(date); //display the selected date in the TextView
        }
    };

    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);
    int style = AlertDialog.THEME_HOLO_LIGHT;

    datePickerDialog = new DatePickerDialog(this,style, dateSetListener, year, month, day);

    }

    private String makeDateString(int day, int month, int year) {
    return getMonthFormat(month) + " " + day + ", " + year;
    }

    private String getMonthFormat(int month) {
    String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    if (month >= 1 && month <= 12) {
        return months[month - 1];
    }
    return "JAN";
    }

    private void showDatePicker() {
    datePickerDialog.show();
    }

    public void Signup(View view){
        Intent intent = new Intent(this, MyAcc.class);
        startActivity(intent);

    }
}
