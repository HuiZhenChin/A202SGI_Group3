package com.example.busybuddy;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busybuddy.R;

import java.util.Calendar;

// Edit User Account Information
public class EditMyAccount extends AppCompatActivity {
    private DBManager dbManager;
    private DatePickerDialog datePickerDialog;
    private ImageButton dateButton;
    private TextView selectedDateView, user;
    EditText mail, pass, rKey;
    String name, dob, email, password, resetkey;
    int ID;
    String usernameValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_my_account);

        // get user information
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        dob = intent.getStringExtra("date");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        resetkey = intent.getStringExtra("key");

        ID = intent.getIntExtra("id", 0);
        Intent intentLogin = getIntent();
        usernameValue = intentLogin.getStringExtra("usernameValue");
        // line below is used to identify whether the id is passed on this page
        // Toast.makeText(getApplicationContext(), String.valueOf(ID), Toast.LENGTH_SHORT).show();

        // date picker function
        initDatePicker();
        // open the database
        dbManager = new DBManager(this);
        dbManager.open();

        dateButton = findViewById(R.id.dateInput);
        selectedDateView = findViewById(R.id.selectedDate);
        user = findViewById(R.id.username);
        mail = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        rKey = findViewById(R.id.phrase);

        // display the editted information
        user.setText(name);
        selectedDateView.setText(dob);
        mail.setText(email);
        pass.setText(password);
        rKey.setText(resetkey);

        // press to select date
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        // return to the Priority List Page
        ImageButton backbutton = findViewById(R.id.back_to_prev);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPrevious();
            }
        });

    }

    // pick date function (scrolling type)
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                selectedDateView.setText(date); // display the selected date in the TextView
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this,style, dateSetListener, year, month, day);

    }

    // convert the selected date to required format
    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + ", " + year;
    }

    // get the month format
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

    // change and update the user information to database
    public void change(View view){
        name = user.getText().toString();
        email = mail.getText().toString();  // update email with the current value from EditText
        password = pass.getText().toString();  // update password with the current value from EditText
        dob = selectedDateView.getText().toString();  // update date of birth with the current value from selectedDateView
        resetkey = rKey.getText().toString();  // update phrase with current value from EditText

        dbManager.update(ID, name, email, password, dob, resetkey);
        Intent iProfile = new Intent(this, MyAccount.class);
        iProfile.putExtra("id", ID);
        iProfile.putExtra("usernameValue", usernameValue);
        startActivity(iProfile);
    }

    public void goPrevious(){
        Intent intentLogin = new Intent(this, MyAccount.class);
        intentLogin.putExtra("usernameValue", usernameValue);
        startActivity(intentLogin);
    }
}