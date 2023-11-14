package com.example.busybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busybuddy.R;

// FAQ: About the App
public class item_select_about_the_app extends AppCompatActivity {
    ImageButton imageButton_about_the_app;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_the_app);

        imageButton_about_the_app = findViewById(R.id.imageButton_about_the_app);

        // get username
        Intent intentLogin = getIntent();
        String usernameValue = intentLogin.getStringExtra("usernameValue");

        // return to the FAQ Main Page
        imageButton_about_the_app.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(item_select_about_the_app.this, FaqMain.class);
                intent.putExtra("usernameValue", usernameValue);
                startActivity(intent);
            }
        });
    }
}