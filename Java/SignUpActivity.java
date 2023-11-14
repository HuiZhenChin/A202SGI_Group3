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

// Sign Up for New User
public class SignUpActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private ImageButton dateButton;
    private TextView selectedDateView, name, email, pass, cpass, userPhrase;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        initDatePicker();

        name = findViewById(R.id.username);
        email = findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);  // password
        cpass = (EditText) findViewById(R.id.confirm_pass);  // confirm password
        dateButton = findViewById(R.id.dateInput);
        selectedDateView = findViewById(R.id.selectedDate);
        userPhrase = findViewById(R.id.phrase); // unique phrase for reset password purpose to identify the user

        // press to select date
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        // open the database
        dbManager = new DBManager(this);
        dbManager.open();
    }

    // date picker set up function
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

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + ", " + year;
    }

    // month format
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

    // sign up function
    public void Signup(View view){
        // get the input string for all information
        String password = pass.getText().toString();
        String cpassword = cpass.getText().toString();
        String user = name.getText().toString();
        String phrase = userPhrase.getText().toString();
        // check for duplicated username
        Boolean duplicateUser = dbManager.nameCheck(user);
        // if entered username does not exist in database (means noone use this username before)
        if(duplicateUser == false) {
            // check for match password
            if (password.equals(cpassword)) {
                final String username = name.getText().toString();
                final String mail = email.getText().toString();
                final String dob = selectedDateView.getText().toString();
                final String phrases = userPhrase.getText().toString();

                // if everything is valid, register successful
                dbManager.register(username, mail, password, dob, phrases);
                Toast.makeText(getApplicationContext(), "Registered Successfully!", Toast.LENGTH_SHORT).show();
                // direct to Login to enter the account
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
            // if password does not match, show error message
            else {
                Toast.makeText(getApplicationContext(), "Password doesn't pair!", Toast.LENGTH_SHORT).show();
            }
        }
        // if entered username has been used by someone else before, show the message
        else{
            Toast.makeText(getApplicationContext(), "Username taken, please choose another username!", Toast.LENGTH_SHORT).show();
        }
    }
}