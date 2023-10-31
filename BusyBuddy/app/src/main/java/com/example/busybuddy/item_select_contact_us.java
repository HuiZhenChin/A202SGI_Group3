package com.example.busybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busybuddy.R;

public class item_select_contact_us extends AppCompatActivity {
    ImageButton imageButton_contact_us;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);

        imageButton_contact_us = findViewById(R.id.imageButton_contact_us);

        imageButton_contact_us.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(item_select_contact_us.this, FaqMain.class);
                startActivity(intent);
            }
        });
    }
}