package com.example.busybuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busybuddy.R;

// My Account (Display user information)
public class MyAccount extends AppCompatActivity {
    private DBManager dbManager;
    String user, dob, email, password, rKey;
    int userID;
    String usernameValue, passwordValue = "";

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);

        // get the user ID
        Intent iProfile = getIntent();
        userID = iProfile.getIntExtra("id", 0); // 0 is a default value if the key is not found
        usernameValue = iProfile.getStringExtra("usernameValue");
        passwordValue = iProfile.getStringExtra("passwordValue");

        // open the database
        dbManager = new DBManager(this);
        dbManager.open();

        // fetch current user
        Cursor cursor = dbManager.fetchUser(userID);

        TextView name = (TextView) findViewById(R.id.username);
        TextView date = (TextView) findViewById(R.id.date);
        TextView mail = (TextView) findViewById(R.id.email);
        TextView pass = (TextView) findViewById(R.id.password);
        TextView key = (TextView) findViewById(R.id.showPhrase);


        if (cursor != null && cursor.moveToFirst()) {
            // if cursor has at least one row, so it's safe to access data.
            user = cursor.getString(cursor.getColumnIndex(DB.USERNAME));
            dob = cursor.getString(cursor.getColumnIndex(DB.DOB));
            email = cursor.getString(cursor.getColumnIndex(DB.EMAIL));
            password = cursor.getString(cursor.getColumnIndex(DB.PASS));
            rKey = cursor.getString(cursor.getColumnIndex(DB.PHRASE));

            // display the text
            name.setText(user);
            date.setText(dob);
            mail.setText(email);
            pass.setText(password);
            key.setText(rKey);
        } else {
            // if cursor is empty, handle the empty cursor case.
        }

        // return to the Priority List Page
        ImageButton backbutton = findViewById(R.id.back_to_prev);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPrevious();
            }
        });

    }

    // press to edit the information
    public void edit_btn(View view){

        Intent intent = new Intent(this, EditMyAccount.class);
        intent.putExtra("name", user);
        intent.putExtra("date", dob);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("id", userID);
        intent.putExtra("key", rKey);
        intent.putExtra("usernameValue", usernameValue);
        startActivity(intent);
    }

    public void goPrevious(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("usernameValue", usernameValue);
        intent.putExtra("passwordValue", passwordValue);
        startActivity(intent);
    }

}