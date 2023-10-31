package com.example.busybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busybuddy.R;

public class item_select_notifications extends AppCompatActivity {
    ImageButton imageButton_notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);

        imageButton_notifications = findViewById(R.id.imageButton_notifications);

        imageButton_notifications.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(item_select_notifications.this, FaqMain.class);
                startActivity(intent);
            }
        });
    }
}