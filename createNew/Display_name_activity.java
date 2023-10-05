package com.example.project3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Display_name_activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Intent intent=getIntent();
        String message = intent.getStringExtra(com.example.project3.MainActivity.name);
        TextView textView = findViewById(R.id.ggg);
        textView.setText(message);
    }

}